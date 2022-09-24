package by.kharchenko.springcafe.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle(Exception ex, Model model) {
        model.addAttribute("url", ex.getStackTrace());
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("exception", ex);
        return "error/404";
    }
}