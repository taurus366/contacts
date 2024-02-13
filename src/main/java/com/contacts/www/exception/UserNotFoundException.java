package com.contacts.www.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserNotFoundException extends RuntimeException {

    private String reason;
    public UserNotFoundException(String reason) {
        super(reason);
        setReason(reason);
    }
    public void setReason(String reason) {
        if(reason == null || reason.isEmpty()) {
            this.reason = "User not found";
        } else {
            this.reason = reason;
        }
    }
}
