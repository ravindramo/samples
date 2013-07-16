package com.cybage.demo.core.exception;

/**
 * Created with IntelliJ IDEA.
 * User: srinivasko
 * Date: 7/9/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Exception to throw when an error has occurred.
 */
public class ErrorException extends RuntimeException {

    protected int status;
    protected String message;

    public ErrorException(String message) {
        super(message);
        this.message = message;
    }

    public ErrorException(int status, String message) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public ErrorException(int status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
