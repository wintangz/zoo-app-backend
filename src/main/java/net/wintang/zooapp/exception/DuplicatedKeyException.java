package net.wintang.zooapp.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DuplicatedKeyException extends Exception {

    private String key;
    private String errorMessage;

    public DuplicatedKeyException(String key) {
        this.key = key;
        this.errorMessage = this.getKey() + " is duplicated";
    }
}
