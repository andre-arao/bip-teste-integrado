package com.example.ejb.exceptions;

import jakarta.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class DataIntegratyViolationException extends RuntimeException{

    public DataIntegratyViolationException(String message) {

        super(message);
    }

}
