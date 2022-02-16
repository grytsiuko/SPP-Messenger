package messenger.backend.auth.exceptions;

import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class InvalidUsernameOrPasswordException extends WebException {

    public InvalidUsernameOrPasswordException() {
        super("Invalid username or password", HttpStatus.BAD_REQUEST);
    }

}
