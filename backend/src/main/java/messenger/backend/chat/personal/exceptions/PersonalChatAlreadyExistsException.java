package messenger.backend.chat.personal.exceptions;

import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class PersonalChatAlreadyExistsException extends WebException {
    public PersonalChatAlreadyExistsException() {
        super("This personal chat already exists", HttpStatus.CONFLICT);
    }
}
