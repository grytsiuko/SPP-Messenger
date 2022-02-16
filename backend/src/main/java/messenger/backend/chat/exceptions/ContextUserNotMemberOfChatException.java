package messenger.backend.chat.exceptions;

import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class ContextUserNotMemberOfChatException extends WebException {
    public ContextUserNotMemberOfChatException() {
        super("The context user is not a member of this chat", HttpStatus.FORBIDDEN);
    }
}
