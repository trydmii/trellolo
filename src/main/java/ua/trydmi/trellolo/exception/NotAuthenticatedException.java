package ua.trydmi.trellolo.exception;

public class NotAuthenticatedException extends RuntimeException {

    public NotAuthenticatedException(String message) {
        super(message);
    }

    public NotAuthenticatedException() {
        super();
    }

}

