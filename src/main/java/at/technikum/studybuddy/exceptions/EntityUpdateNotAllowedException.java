package at.technikum.studybuddy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class EntityUpdateNotAllowedException extends RuntimeException {
    public EntityUpdateNotAllowedException() {
    }

    public EntityUpdateNotAllowedException(String message) {
        super(message);
    }

    public EntityUpdateNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityUpdateNotAllowedException(Throwable cause) {
        super(cause);
    }

    public EntityUpdateNotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
