package com.pasha.licenseservice.controller;

import static java.util.Collections.singletonMap;

import com.pasha.licenseservice.model.utils.ErrorMessage;
import com.pasha.licenseservice.model.utils.ResponseWrapper;
import com.pasha.licenseservice.model.utils.RestErrorList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@EnableWebMvc
public class ExceptionController extends ResponseEntityExceptionHandler {


    /**
     * handleException - Handles all the Exception recieving a request, responseWrapper.
     *@param request
     *@param responseWrapper
     *@return ResponseEntity<ResponseWrapper>
     */
    @ExceptionHandler(value = { Exception.class })
    public @ResponseBody ResponseEntity<ResponseWrapper> handleException(ResponseWrapper responseWrapper){

        return ResponseEntity.ok(responseWrapper);
    }

    /**
     * handleIOException - Handles all the Authentication Exceptions of the application.
     *@param request
     *@param exception
     *@return ResponseEntity<ResponseWrapper>

     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseWrapper> handleIOException(RuntimeException e){

        RestErrorList errorList = new RestErrorList(HttpStatus.NOT_ACCEPTABLE, new ErrorMessage(e.getMessage(), e.getMessage()));
        ResponseWrapper responseWrapper = new ResponseWrapper(null, singletonMap("status", HttpStatus.NOT_ACCEPTABLE), errorList);


        return ResponseEntity.ok(responseWrapper);
    }

}