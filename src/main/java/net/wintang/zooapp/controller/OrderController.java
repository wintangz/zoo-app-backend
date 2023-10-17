package net.wintang.zooapp.controller;

import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.wintang.zooapp.dto.request.OrderRequestDTO;
import net.wintang.zooapp.dto.response.OrderResponseDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.service.IEmailService;
import net.wintang.zooapp.service.IOrderService;
import net.wintang.zooapp.service.IPaymentService;
import net.wintang.zooapp.util.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final IOrderService orderService;

    private final IPaymentService paymentService;

    private final IEmailService emailService;

    @Autowired
    public OrderController(IOrderService orderService, IPaymentService paymentService, IEmailService emailService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createOrder(@Valid @RequestBody OrderRequestDTO order, BindingResult bindingResult, HttpServletRequest req) throws UnsupportedEncodingException, SignatureException, NoSuchAlgorithmException, InvalidKeyException {

        if (bindingResult.hasErrors()) {

        }
        return paymentService.getPaymentUrl((OrderResponseDTO) orderService.createOrder(order).getBody().getData(), req);
    }

    @GetMapping("/payment")
    public ResponseEntity<ResponseObject> Transaction(
            @RequestParam(value = "vnp_Amount") String amount,
            @RequestParam(value = "vnp_BankCode") String bankCode,
            @RequestParam(value = "vnp_OrderInfo") String orderInfo,
            @RequestParam(value = "vnp_ResponseCode") String resCode,
            @RequestParam(value = "vnp_TxnRef") String id
    ) throws NotFoundException, SignatureException, NoSuchAlgorithmException, IOException, InvalidKeyException, WriterException {
        ResponseObject response = new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                ApplicationConstants.ResponseMessage.INVALID, "");

        if (resCode.equals("00")) {//User paid successfully
            response.setStatus(ApplicationConstants.ResponseStatus.OK);
            response.setMessage(ApplicationConstants.ResponseMessage.SUCCESS);
            response.setData("User paid successfully!");
            paymentService.setPaymentStatus(id);
            emailService.sendEmail(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

//    @PostMapping("/verification")
//    public ResponseEntity<ResponseObject> checkTickets() {
//        return orderService.verifyTickets;
//    }
}
