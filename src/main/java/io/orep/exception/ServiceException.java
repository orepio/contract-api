package io.orep.exception;

public class ServiceException extends RuntimeException {

    public ServiceException(String format, Object... objects) {
        super(String.format(format, objects));
    }

}