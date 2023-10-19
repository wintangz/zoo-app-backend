package net.wintang.zooapp.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ErrorResponseObject {

    private String status;
    private Object serverError;
    private Object clientErrors;

    public ErrorResponseObject(String status, Object serverError, Object clientErrors) {
        this.status = status;
        this.serverError = serverError;
        this.clientErrors = clientErrors;
    }
}
