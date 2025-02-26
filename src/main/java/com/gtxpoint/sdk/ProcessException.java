package com.gtxpoint.sdk;

/**
 * Exception that throws when has some errors in payment flow
 */
public class ProcessException extends Exception
{
    /**
     * Can be with exception
     * @param exception exception
     */
    public ProcessException(Throwable exception) {
        super(exception);
    }

    /**
     * Can be with message
     * @param message exception message
     */
    public ProcessException(String message) {
        super(message);
    }
}
