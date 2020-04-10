package com.bengodwinweb.pettycash.dto.model.error;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ApiSubError {
    private String field;
    private String message;

    public ApiSubError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public ApiSubError(String message) {
        this.message = message;
    }
}
