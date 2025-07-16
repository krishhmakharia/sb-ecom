package com.ecom.project.exceptions;

public class APIException extends RuntimeException{
    private static final Long serialVersionUId = 1L;
    public APIException(){

    }
    public APIException(String message){
        super(message);
    }
}
