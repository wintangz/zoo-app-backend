package net.wintang.zooapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    @NotBlank(message = "Payment method cannot be empty")
    private String paymentMethod;

    @Data
    public static class TicketItem {
        private int ticketId;
        private int quantity;
    }

    @NotNull(message = "Tickets cannot be empty")
    private List<TicketItem> ticketItems;
}

