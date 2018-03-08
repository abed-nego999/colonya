package sngular.com.atmsmap.presentation.exception;


public class NotNetworkException extends Exception {

    public NotNetworkException() {
        super("No internet connection");
    }
}
