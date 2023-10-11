package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.OrderRequestDTO;
import net.wintang.zooapp.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IOrderService {
    ResponseEntity<ResponseObject> getOrders();

    ResponseEntity<ResponseObject> createOrder(OrderRequestDTO order, String username);
}
