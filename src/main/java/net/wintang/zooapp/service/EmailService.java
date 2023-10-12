package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.EmailRequestDetails;
import net.wintang.zooapp.entity.Order;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.repository.OrderDetailRepository;
import net.wintang.zooapp.repository.OrderRepository;
import net.wintang.zooapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

    private final JavaMailSender javaMailSender;

    private final UserRepository userService;

    private final OrderRepository orderService;

    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, UserRepository userService, OrderRepository orderService, OrderDetailRepository orderDetailRepository) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
        this.orderService = orderService;
        this.orderDetailRepository = orderDetailRepository;
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
    public String sendEmail(String id) {
        int orderId = Integer.parseInt(id);
        User customer = orderService.findById(orderId).orElse(new Order()).getCustomer();

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setSubject("Thanks for your order: #" + orderId + ". Your tickets are ready!");
            mailMessage.setCc("Chúng em muốn ra hội đồng.");
            mailMessage.setTo(customer.getEmail());
            mailMessage.setText("We’ve received your order. Thank you for choosing our Zoo!" +
                    "\nYour bill: " +
                    "\n" + orderService.findById(orderId) +
                    "\nYour tickets: " +
                    "\n" + orderDetailRepository.findAllByOrderId(orderId));
            javaMailSender.send(mailMessage);
            System.out.println("Sent Email to someone");
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
}
