package messenger.backend.auth.exceptions;

import messenger.backend.utils.exceptions.WebException;
import org.springframework.http.HttpStatus;


public class JwtAuthException extends WebException {

    public JwtAuthException() {
        super("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
    }

}
