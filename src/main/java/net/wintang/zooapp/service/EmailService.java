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
import net.wintang.zooapp.repository.UserRepository;
import net.wintang.zooapp.security.JwtGenerator;
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
import java.util.Random;

@Service
public class EmailService implements IEmailService {

    private final JavaMailSender javaMailSender;

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final UserRepository userRepository;

    private final AzureStorageService azureStorageService;

    private final JwtGenerator jwtGenerator;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, OrderRepository orderService, OrderDetailRepository orderDetailRepository, UserRepository userRepository, AzureStorageService azureStorageService, JwtGenerator jwtGenerator) {
        this.javaMailSender = javaMailSender;
        this.orderRepository = orderService;
        this.orderDetailRepository = orderDetailRepository;
        this.userRepository = userRepository;
        this.azureStorageService = azureStorageService;
        this.jwtGenerator = jwtGenerator;
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
            emailContent.append("<h1 style='text-align: center'>We have received your order. Thank you for choosing our Zoo!</h1>" + "<div style='text-align: center'><br>Your order no: ")
                    .append(order.getId())
                    .append("<br>Purchased at: ")
                    .append(order.getCreatedDate().toString().substring(0, 19).replace("T", " "))
                    .append("<br>Your tickets:<br/></div>")
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

    @Override
    public String sendResetPasswordMail(User user) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            Random random = new Random();
            int code = random.nextInt(900000) + 100000;
            mailMessage.setFrom(sender);
            mailMessage.setTo(user.getEmail());
            mailMessage.setText("If you didn't send this request, please ignore this email.\n" +
                    "Your verification code is " + code);
            mailMessage.setSubject("Reset password for user " + user.getUsername());
            javaMailSender.send(mailMessage);
            String resetToken = jwtGenerator.generateToken(user.getEmail(), String.valueOf(code));
            user.setResetToken(resetToken);
            userRepository.save(user);
            return "Email sent to user";
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
}
