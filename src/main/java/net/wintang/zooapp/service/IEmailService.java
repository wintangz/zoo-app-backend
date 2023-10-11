package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.EmailRequestDetails;

public interface IEmailService {
    String sendSimpleEmail(EmailRequestDetails emailDetails);

    String sendEmail(EmailRequestDetails emailDetails);
}
