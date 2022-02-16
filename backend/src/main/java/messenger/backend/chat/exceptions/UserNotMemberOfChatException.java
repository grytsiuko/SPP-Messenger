package messenger.backend.chat.exceptions;

import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class UserNotMemberOfChatException extends WebException {
    public UserNotMemberOfChatException() {
        super("The user is not a member of this chat", HttpStatus.FORBIDDEN);
    }
}
