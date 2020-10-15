package com.assignment.weekTwo.MyExceptionController;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(int id) {
        super("Order not found with id : "+id);
    }
}
