package com.saar.blog.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.saar.blog.payloads.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
	{
		String msg=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(msg,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	// Code for Constraints 
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
		Map<String,String>resp=new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error->{
			String fieldName=((FieldError) error).getField();
			String message=error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
		/*
		 1. ex.getBindingResult() क्या है?
		MethodArgumentNotValidException के अंदर एक Method getBindingResult() होता है। यह वह Data Object है, जो Input Validation की सभी जानकारी और Errors को स्टोर करता है।

		क्या लौटाता है?
		BindingResult Object: इसमें Validation के सभी फेल हुए Results (Errors) होते हैं।
		2. .getAllErrors() क्या है?
		यह Method BindingResult से सभी Errors को एक List के रूप में निकालता है।

		क्या लौटाता है?
		List<ObjectError>: इसमें हर Validation Error का विवरण होता है।
		
	3. .forEach(error -> { ... }) क्या है?
	यह एक Lambda Expression है, जो Error की List पर Iteration (हर Item पर ऑपरेशन) करता है।
	हर Error (ObjectError) को error नाम से लिया गया है।
	क्या करता है?
	List के हर Error के लिए कोड का ब्लॉक { ... } चलाया जाता है।
	
	4. String fieldName = ((FieldError) error).getField(); क्या है?
	error को FieldError में Cast किया गया है।
	FieldError उस Field की Error Details को पकड़ने के लिए उपयोगी है जो Validation फेल हुई है।
	getField():
	वह Field का नाम लौटाता है, जिसमें Validation Error हुआ है।
	उदाहरण: अगर name Field गलत है, तो यह name लौटाएगा।
	
	5. String message = error.getDefaultMessage(); क्या है?
	getDefaultMessage() हर Error के लिए Default Validation Message देता है।
	यह Validation Annotation में लिखा हुआ Message हो सकता है, जैसे:
	@NotBlank(message = "Name cannot be blank")
	या, Spring का Default Message।
	क्या लौटाता है?
	वह Error Message जो क्लाइंट को समझाने के लिए उपयोगी है।
	उदाहरण: "Name cannot be blank"।
	*/
	}
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handlerApiException(ApiException ex)
	{
		String msg=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(msg,true);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
}
