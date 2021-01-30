package com.vikasKrSherawat.payroll.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(long id){
        super(String.format("Order %s not found", id));
    }
}
