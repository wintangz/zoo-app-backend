package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.OrderDTO;
import net.wintang.zooapp.util.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IOrderService {
    ResponseEntity<ResponseObject> getAllOrders();

    ResponseEntity<ResponseObject> createNewOrder(OrderDTO order, String username);
}
