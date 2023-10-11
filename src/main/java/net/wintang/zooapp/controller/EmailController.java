package net.wintang.zooapp.controller;

import net.wintang.zooapp.dto.request.EmailRequestDetails;
import net.wintang.zooapp.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private final IEmailService emailService;

    @Autowired
    public EmailController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("")
    public String
    sendMail(@RequestBody EmailRequestDetails details) {
        return emailService.sendSimpleEmail(details);
    }
}
