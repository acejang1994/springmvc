package com.stl.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.stl.api.util.StatusCode;
import com.stl.api.util.StatusCodeType;
import com.stl.api.util.StatusCodes;
import com.stl.common.models.serializers.ObjectParsingException;

/**
 * This class catches and handles exceptions thrown within or before entering
 * other controller end point methods.
 * 
 * @author markroper
 *
 */
@ControllerAdvice
public class RestErrorHandler extends BaseController {

    @Autowired
    public RestErrorHandler(MessageSource messageSource) {
        
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class) 
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<StatusCode> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        StatusCode error = new StatusCode(StatusCodes.UNPARSABLE_REQUEST_CODE, result.getFieldError().getDefaultMessage());
        return new ResponseEntity<StatusCode>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(JsonMappingException.class) 
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<StatusCode> processJsonMappingException(JsonMappingException ex) {
        StatusCode error = new StatusCode(StatusCodes.JSON_PARSING_ERROR_CODE, ex.getMessage());
        return new ResponseEntity<StatusCode>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ObjectParsingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<StatusCode> processAppJsonMappingException(ObjectParsingException ex) {
        StatusCode returnCode = StatusCodes.getStatusCode(StatusCodeType.UNSUPPORTED_ASSIGNMENT_TYPE, ex.getArgs());
        returnCode.setMessage(ex.getMessage());
        return respond(returnCode);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<StatusCode> processGeneralException(Exception ex) {
        return respond(StatusCodes.getStatusCode(StatusCodeType.UNKNOWN_INTERNAL_SERVER_ERROR));
    }
}
