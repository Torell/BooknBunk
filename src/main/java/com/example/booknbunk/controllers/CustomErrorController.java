package com.example.booknbunk.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;


import static org.springframework.http.HttpStatus.FORBIDDEN;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(FORBIDDEN)
    public ModelAndView handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/403");
        return modelAndView;
    }
}