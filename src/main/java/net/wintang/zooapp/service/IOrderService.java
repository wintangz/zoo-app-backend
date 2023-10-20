package net.wintang.zooapp.service;

import net.wintang.zooapp.dto.request.OrderRequestDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.exception.PermissionDeniedException;
import org.springframework.http.ResponseEntity;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public interface IOrderService {
    ResponseEntity<ResponseObject> getOrders();

    ResponseEntity<ResponseObject> getPurchasedTickets();

    ResponseEntity<ResponseObject> getPurchasedTicketsByOrderId(int id) throws NotFoundException;

    ResponseEntity<ResponseObject> createOrder(OrderRequestDTO orderDto);

    ResponseEntity<ResponseObject> verifyTickets(int orderId, int customerId, int ticketId, String ticketType, LocalDateTime issuedDate, String hashData) throws NoSuchAlgorithmException, InvalidKeyException, NotFoundException, PermissionDeniedException;

    ResponseEntity<ResponseObject> getOrderById(int id) throws NotFoundException;
}
