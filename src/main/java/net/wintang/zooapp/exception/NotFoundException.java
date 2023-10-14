package net.wintang.zooapp.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotFoundException extends Exception {

    private String item;
    private String errorMessage;

    public NotFoundException(String item) {
        this.item = item;
        this.errorMessage = this.getItem() + " is not found";
    }
}