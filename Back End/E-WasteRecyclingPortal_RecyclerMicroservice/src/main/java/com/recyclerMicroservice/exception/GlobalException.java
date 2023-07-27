package com.recyclerMicroservice.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalException {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName;
			if (error instanceof FieldError) {
				fieldName = ((FieldError) error).getField();
			} else {
				fieldName = error.getObjectName();
			}
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return ResponseEntity.badRequest().body(errors);
	}


    @ExceptionHandler(value = RecyclerNotFoundException.class)
    public ResponseEntity<String> handleRecyclerNotFoundException(RecyclerNotFoundException ex) {
        return new ResponseEntity<>("Recycler Not Found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NoContentFoundException.class)
    public ResponseEntity<String> handleNoContentFoundException(NoContentFoundException ex) {
        return new ResponseEntity<>("No Content Found!!!", HttpStatus.CONFLICT);
    }
}
