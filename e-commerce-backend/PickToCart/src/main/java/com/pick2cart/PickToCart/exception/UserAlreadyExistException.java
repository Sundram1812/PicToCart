package com.pick2cart.PickToCart.exception;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException() {
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
