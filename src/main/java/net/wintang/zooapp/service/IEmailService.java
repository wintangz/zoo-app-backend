package net.wintang.zooapp.service;

import com.google.zxing.WriterException;
import net.wintang.zooapp.dto.request.EmailRequestDetails;
import net.wintang.zooapp.entity.User;
import net.wintang.zooapp.exception.NotFoundException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface IEmailService {
    String sendSimpleEmail(EmailRequestDetails emailDetails);

    String sendEmail(String id) throws NotFoundException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, WriterException, IOException;

    String sendResetPasswordMail(User user);
}
