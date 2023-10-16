package net.wintang.zooapp.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ErrorResponseObject {

    private String status;
    private String serverError;
    private List<String> clientErrors;

    public ErrorResponseObject(String status, String serverError) {
        this.status = status;
        this.serverError = serverError;
    }

    public ErrorResponseObject(String status, List<String> clientErrors) {
        this.status = status;
        this.clientErrors = clientErrors;
    }
}
