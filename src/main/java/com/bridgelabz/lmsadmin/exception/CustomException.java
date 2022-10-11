package com.bridgelabz.lmsadmin.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException {
    private int errorCode;
    private String message;
}
