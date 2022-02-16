package messenger.backend.user.exceptions;

import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;


public class UserNotFoundException extends WebException {

    public UserNotFoundException() {
        super("User not found", HttpStatus.NOT_FOUND);
    }
    public UserNotFoundException(String userType) {
        super("The " + userType + " user not found", HttpStatus.NOT_FOUND);
    }

}
