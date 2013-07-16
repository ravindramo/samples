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
 * Exception to throw when an Requested resource is not found.
 */
public class ResourceNotFound extends ErrorException {
    public static final String MESSAGE = "Requested resource could not be found";

    public ResourceNotFound(String message) {
        super(Response.Status.NOT_FOUND.getStatusCode(), message);
    }

    public ResourceNotFound(String message, Throwable cause) {
        super(Response.Status.NOT_FOUND.getStatusCode(), message, cause);
    }
}
