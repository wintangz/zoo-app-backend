package net.wintang.zooapp.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDeniedException extends Exception {

    private String errorMessage;

    public PermissionDeniedException() {
        this.errorMessage = "You don't have permission to do this";
    }

    public PermissionDeniedException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
