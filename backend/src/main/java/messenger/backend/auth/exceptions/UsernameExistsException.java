package messenger.backend.auth.exceptions;

import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class UsernameExistsException extends WebException {

    public UsernameExistsException() {
        super("User with such username already exists", HttpStatus.BAD_REQUEST);
    }

}
