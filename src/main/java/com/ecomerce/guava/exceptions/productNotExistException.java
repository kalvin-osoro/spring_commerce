package com.ecomerce.guava.exceptions;

public class productNotExistException extends IllegalArgumentException {
    public productNotExistException(String msg) {
        super(msg);
    }
}
