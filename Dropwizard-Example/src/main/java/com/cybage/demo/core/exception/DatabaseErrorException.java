package com.cybage.demo.core.exception;

import javax.ws.rs.core.Response;

/**
 * Created with IntelliJ IDEA.
 * User: srinivasko
 * Date: 7/9/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Exception thrown when a server error occurs that cannot be recovered from.
 */
public class DatabaseErrorException extends ErrorException {
    public static final String MESSAGE = "The Database startup failed.";

    public DatabaseErrorException(String message) {
        super(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), message);
    }

    public DatabaseErrorException(String message, Throwable cause) {
        super(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), message, cause);
    }
}
