package net.wintang.zooapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.wintang.zooapp.dto.request.OrderRequestDTO;
import net.wintang.zooapp.dto.response.OrderResponseDTO;
import net.wintang.zooapp.service.IOrderService;
import net.wintang.zooapp.service.IPaymentService;
import net.wintang.zooapp.util.ResponseObject;
import net.wintang.zooapp.util.TokenExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

@RestController
@RequestMapping("/api/orders")
public class PaymentController {

    private final IOrderService orderService;

    private final IPaymentService paymentService;

    @Autowired
    public PaymentController(IOrderService orderService, IPaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createOrder(@RequestHeader("Authorization") String token, @Valid @RequestBody OrderRequestDTO order, BindingResult bindingResult, HttpServletRequest req) throws UnsupportedEncodingException, SignatureException, NoSuchAlgorithmException, InvalidKeyException {

        if (bindingResult.hasErrors()) {

        }

        TokenExtractor tokenExtractor = new TokenExtractor();
        String username = tokenExtractor.getUsername(token);
        return paymentService.getPaymentUrl((OrderResponseDTO) orderService.createOrder(order, username).getBody().getData(), req);
    }
}
