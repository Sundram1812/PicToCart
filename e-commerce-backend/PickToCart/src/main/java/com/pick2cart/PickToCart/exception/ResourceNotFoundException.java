package com.pick2cart.PickToCart.exception;

public class ResourceNotFoundException extends Exception{


    public ResourceNotFoundException(String resourceName, Long resourceId) {
         super(resourceName+" not found with given id "+resourceId);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
