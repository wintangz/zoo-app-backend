package net.wintang.zooapp.controller;

import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.wintang.zooapp.dto.request.OrderRequestDTO;
import net.wintang.zooapp.dto.response.OrderResponseDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.exception.PermissionDeniedException;
import net.wintang.zooapp.service.IEmailService;
import net.wintang.zooapp.service.IOrderService;
import net.wintang.zooapp.service.IPaymentService;
import net.wintang.zooapp.util.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.LocalDateTime;

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

    @GetMapping
    public ResponseEntity<ResponseObject> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<ResponseObject> getOrdersByCustomerId(@PathVariable int id) {
        return orderService.getOrdersByCustomerId(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrderById(@PathVariable int id) throws NotFoundException {
        return orderService.getOrderById(id);
    }

    @GetMapping("/tickets")
    public ResponseEntity<ResponseObject> getPurchasedTickets() {
        return orderService.getPurchasedTickets();
    }

    @GetMapping("/{id}/tickets")
    public ResponseEntity<ResponseObject> getPurchasedTicketsByOrderId(@PathVariable int id) throws NotFoundException {
        return orderService.getPurchasedTicketsByOrderId(id);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createOrder(@Valid @RequestBody OrderRequestDTO order, HttpServletRequest req) throws UnsupportedEncodingException, SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        return paymentService.getPaymentUrl((OrderResponseDTO) orderService.createOrder(order).getBody().getData(), req);
    }

    @GetMapping("/payment")
    public ResponseEntity<ResponseObject> Transaction(
            @RequestParam("vnp_Amount") String amount,
            @RequestParam("vnp_BankCode") String bankCode,
            @RequestParam("vnp_OrderInfo") String orderInfo,
            @RequestParam("vnp_ResponseCode") String resCode,
            @RequestParam("vnp_TxnRef") String id
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

    @GetMapping("/verification")
    public ResponseEntity<ResponseObject> checkTickets(
            @RequestParam("orderId") int orderId,
            @RequestParam("customerId") int customerId,
            @RequestParam("ticket") int ticketId,
            @RequestParam("type") String ticketType,
            @RequestParam("issuedDate") LocalDateTime issuedDate,
            @RequestParam("hashData") String hashData
    ) throws NoSuchAlgorithmException, InvalidKeyException, NotFoundException, PermissionDeniedException {
        return orderService.verifyTickets(orderId, customerId, ticketId, ticketType, issuedDate, hashData);
    }
}
