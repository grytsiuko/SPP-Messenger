package messenger.backend.chat.group.exceptions;


import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class UserNotInChatException extends WebException {
    public UserNotInChatException() {
        super("User is not a member of this chat", HttpStatus.NOT_FOUND);
    }
}
