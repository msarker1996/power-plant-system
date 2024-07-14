package com.brac.power.plant.system.exception;

public class UserNotFoundException  extends Exception{ //RunTimeException
    public UserNotFoundException(String message) {
        super(message);
    }
}
