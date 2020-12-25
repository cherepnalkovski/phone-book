package com.demo.phonebook.controller;

import com.demo.phonebook.exception.ExceptionMessage;
import com.demo.phonebook.exception.PayloadValidationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PhoneBookControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final PayloadValidationResponse validationException(MethodArgumentNotValidException e) {
        PayloadValidationResponse payloadValidationResponse = new PayloadValidationResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            payloadValidationResponse.getViolations().add(
                    new ExceptionMessage("INVALID_PAYLOAD", fieldError.getField()));
        }

        return payloadValidationResponse;
    }
}
