package net.wintang.zooapp.controller;

import net.wintang.zooapp.dto.OrderDTO;
import net.wintang.zooapp.service.IOrderService;
import net.wintang.zooapp.util.ResponseObject;
import net.wintang.zooapp.util.TokenExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class PaymentController {

    private final IOrderService orderService;

    @Autowired
    public PaymentController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createOrder(@RequestHeader("Authorization") String token, @RequestBody OrderDTO order) {
        TokenExtractor tokenExtractor = new TokenExtractor();
        String username = tokenExtractor.getUsername(token);
        return orderService.createNewOrder(order, username);
    }
}
