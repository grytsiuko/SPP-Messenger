package messenger.backend.chat.exceptions;

import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class TargetUserNotMemeberOfChatException extends WebException {

    public TargetUserNotMemeberOfChatException() {
        super("The target user is not a member of this chat", HttpStatus.FORBIDDEN);
    }
}
