package messenger.backend.utils.exceptions;

import org.springframework.http.HttpStatus;

public class ValidationException extends WebException {

    public ValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
