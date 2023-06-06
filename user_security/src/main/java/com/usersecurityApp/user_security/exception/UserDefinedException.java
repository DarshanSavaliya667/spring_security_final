package com.usersecurityApp.user_security.exception;

public class UserDefinedException  extends RuntimeException{

    private static final long serialVersionUID = 275376217920091065L;

    public UserDefinedException(final String message) {
        super(message);
    }
}
