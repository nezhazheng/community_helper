package com.communityhelper.api;

import static com.communityhelper.api.APIResponse.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SystemErrorHandler extends ResponseEntityExceptionHandler {
    
    private Logger logger = LoggerFactory.getLogger(SystemErrorHandler.class);
    
    @ExceptionHandler(Throwable.class)
    public 
    @ResponseBody
    APIResponse handleThrowableException(Throwable exception, WebRequest request) {
        logger.error("系统错误", exception);
        return fail("系统错误");
    }
}
