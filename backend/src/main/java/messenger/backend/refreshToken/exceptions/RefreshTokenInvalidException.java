package messenger.backend.refreshToken.exceptions;

import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;

public class RefreshTokenInvalidException extends WebException {

    public RefreshTokenInvalidException() {
        super("Refresh token invalid or not found", HttpStatus.UNAUTHORIZED);
    }

}
