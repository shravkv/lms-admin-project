package com.bridgelabz.lmsadmin.exception;


import com.bridgelabz.lmsadmin.util.ResponseClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class LMSExceptionHandler {

    @ExceptionHandler(LMSException.class)
    public ResponseEntity<ResponseClass> handleHiringException(LMSException exception) {
        ResponseClass response = new ResponseClass();
        response.setErrorCode(400);
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomException> handleHiringException(MethodArgumentNotValidException exception) {
        CustomException response = new CustomException();
        response.setErrorCode(400);
        response.setMessage(exception.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
