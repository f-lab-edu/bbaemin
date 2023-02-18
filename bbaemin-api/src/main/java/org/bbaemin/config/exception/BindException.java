package org.bbaemin.config.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class BindException extends RuntimeException {

    private BindingResult bindingResult;

    public BindException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}
