package net.wintang.zooapp.service;

import jakarta.servlet.http.HttpServletRequest;
import net.wintang.zooapp.dto.response.OrderResponseDTO;
import net.wintang.zooapp.util.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface IPaymentService {
    ResponseEntity<ResponseObject> getPaymentUrl(OrderResponseDTO order, HttpServletRequest req) throws UnsupportedEncodingException, SignatureException, NoSuchAlgorithmException, InvalidKeyException;
}
