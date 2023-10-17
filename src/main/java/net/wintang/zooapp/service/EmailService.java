package net.wintang.zooapp.service;

import com.google.zxing.WriterException;
import jakarta.mail.internet.MimeMessage;
import net.wintang.zooapp.dto.request.EmailRequestDetails;
import net.wintang.zooapp.entity.Order;
import net.wintang.zooapp.entity.OrderDetail;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.repository.OrderDetailRepository;
import net.wintang.zooapp.repository.OrderRepository;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.util.Encryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService implements IEmailService {

    private final JavaMailSender javaMailSender;

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final AzureStorageService azureStorageService;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, OrderRepository orderService, OrderDetailRepository orderDetailRepository, AzureStorageService azureStorageService) {
        this.javaMailSender = javaMailSender;
        this.orderRepository = orderService;
        this.orderDetailRepository = orderDetailRepository;
        this.azureStorageService = azureStorageService;
    }

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendSimpleEmail(EmailRequestDetails details) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    @Override
    public String sendEmail(String id) throws NotFoundException, NoSuchAlgorithmException, InvalidKeyException, WriterException, IOException {
        int orderId = Integer.parseInt(id);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException(id));
        User customer = order.getCustomer();
        String data = "?orderId=" + orderId + "&customerId=" + customer.getId();
        List<OrderDetail> tickets = orderDetailRepository.findAllByOrderId(orderId);
        Map<Integer, String> ticketsWithImgUrl = new HashMap<>();

        for (OrderDetail t : tickets) {
            String ticketData = data + "&ticket=" + t.getId() + "&type=" + t.getTicket().getName() + "&issuedDate=" + t.getOrder().getCreatedDate() + "&hashData=";
            String encryptedData = Encryptor.calculateHMAC(ApplicationConstants.Keys.KEY, ticketData);
            BufferedImage qrCodeImage = QRGeneratorService.generateQRCode(ticketData + encryptedData, 512, 512);

            // Convert BufferedImage to Base64
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "png", bos);
            byte[] imageBytes = bos.toByteArray();
            String imgUrl = azureStorageService.uploadImageToBlobStorage("qrcode_" + t.getOrder().getId() + "_" + t.getId(), imageBytes);
            ticketsWithImgUrl.put(t.getId(), imgUrl);
        }

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender);
            helper.setTo(customer.getEmail());
            helper.setSubject("Thanks for your order: #" + orderId + ". Your tickets are ready!");

            // Add image attachments inline in the email
            StringBuilder emailContent = new StringBuilder();
            emailContent.append("<h1>We have ve received your order. Thank you for choosing our Zoo!</h1>" + "<br>Your order no: ")
                    .append(order.getId())
                    .append("<br>Purchased at: ")
                    .append(order.getCreatedDate().toString().replace("T", " "))
                    .append("<br>Your tickets:<br/>")
                    .append("<div style='text-align: center'>");
            ticketsWithImgUrl.forEach((ticketId, imgUrl) ->
                    emailContent.append("<h2>Ticket no.")
                            .append(ticketId)
                            .append("</h2><br/>")
                            .append("<img src='")
                            .append(imgUrl)
                            .append("' alt='qrcode' />")
            );
            emailContent.append("</div>");
            helper.setText(emailContent.toString(), true);

            javaMailSender.send(mimeMessage);
            System.out.println("Sent Email to someone");
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

//    @Override
//    public String sendEmail(String id) throws NotFoundException, NoSuchAlgorithmException, InvalidKeyException, WriterException {
//        int orderId = Integer.parseInt(id);
//        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException(id));
//        User customer = order.getCustomer();
//        String data = "orderId=" + orderId
//                + "&customerId=" + customer.getId();
//        List<OrderDetail> tickets = orderDetailRepository.findAllByOrderId(orderId);
//        StringBuilder ticketsList = new StringBuilder();
//        List<BufferedImage> qrCodes = new ArrayList<>();
//        for (OrderDetail t : tickets) {
//            String ticket = ticketsList.append("\n")
//                    .append(data)
//                    .append("&ticket=")
//                    .append(t.getId())
//                    .append("&type=")
//                    .append(t.getTicket().getName()).toString();
//            String encryptedData = Encryptor.calculateHMAC(ApplicationConstants.Keys.KEY, ticket);
//            qrCodes.add(QRGeneratorService.generateQRCode(ticket + encryptedData, 512, 512));
//        }
//        try {
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setFrom(sender);
//            mailMessage.setSubject("Thanks for your order: #" + orderId + ". Your tickets are ready!");
//            mailMessage.setCc("Chúng em muốn ra hội đồng.");
//            mailMessage.setTo(customer.getEmail());
//            mailMessage.setText("We’ve received your order. Thank you for choosing our Zoo!" +
//                    "\nYour bill: " +
//                    "\n" + orderRepository.findById(orderId) +
//                    "\nYour tickets: " +
//                    "\n" + qrCodes);
//            javaMailSender.send(mailMessage);
//            System.out.println("Sent Email to someone");
//            return "Mail Sent Successfully...";
//        } catch (Exception e) {
//            return "Error while Sending Mail";
//        }
}
