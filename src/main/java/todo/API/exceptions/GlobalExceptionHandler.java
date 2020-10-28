package todo.API.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BigLengthException.class)
    ResponseEntity<String> handleBigLengthException() {
        return new ResponseEntity<String>(
                "Length must be lesser than 250 symbols",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    ResponseEntity<String> handleConflictException() {
        return new ResponseEntity<String>(
                "An object with the same parameters already exists",
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<String> handleNotFoundException() {
        return new ResponseEntity<String>(
                "An object with that ID is not exists",
                HttpStatus.NOT_FOUND);
    }
}
