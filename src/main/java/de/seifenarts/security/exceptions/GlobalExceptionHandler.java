package de.seifenarts.security.exceptions;


import de.seifenarts.security.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 401 Unauthorized – плохой логин/пароль
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException ex) {
        return buildError(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage());
    }

    // 401 Unauthorized – невалидный токен
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiError> handleInvalidToken(InvalidTokenException ex) {
        return buildError(HttpStatus.UNAUTHORIZED, "Invalid token", ex.getMessage());
    }

    // 404 – пользователь не найден
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, "User not found", ex.getMessage());
    }

    // 404 – любая сущность не найдена (например Order, Customer)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, "Resource not found", ex.getMessage());
    }

    // 400 – ошибки валидации DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");
        return buildError(HttpStatus.BAD_REQUEST, "Validation failed", message);
    }

    // 500 – неожиданные ошибки
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralErrors(Exception ex) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", ex.getMessage());
    }

    // ---------------- utility method ----------------

    private ResponseEntity<ApiError> buildError(HttpStatus status, String error, String message) {
        ApiError apiError = new ApiError(
                status.value(),
                error,
                message,
                java.time.LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(apiError);
    }
}
