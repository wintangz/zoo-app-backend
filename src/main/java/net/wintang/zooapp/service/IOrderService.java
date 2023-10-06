package net.wintang.zooapp.service;

import net.wintang.zooapp.model.OrderDTO;
import net.wintang.zooapp.util.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IOrderService {
    ResponseEntity<ResponseObject> getAllOrders();

    ResponseEntity<ResponseObject> createNewOrder(OrderDTO order, String username);
}
