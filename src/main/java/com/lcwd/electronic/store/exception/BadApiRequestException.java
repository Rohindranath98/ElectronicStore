package com.lcwd.electronic.store.exception;

public class BadApiRequestException extends RuntimeException{
    public BadApiRequestException(String meassage){
        super(meassage);
    }
    public BadApiRequestException(){
        super("Bad Request");
    }
}
