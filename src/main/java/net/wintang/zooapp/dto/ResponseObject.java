package net.wintang.zooapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseObject {

    private String status;
    private String message;
    private List<String> errorMessages;
    private Object data;

    public ResponseObject(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseObject(String status, List<String> messages, Object data) {
        this.status = status;
        this.errorMessages = messages;
        this.data = data;
    }
}
