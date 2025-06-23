package com.ToDoList2.ToDoList2.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ToDoList2.ToDoList2.controller.PageController;

@ControllerAdvice(assignableTypes = PageController.class)
public class HtmlExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("popupErrorMessage", e.getMessage());
        return "redirect:/ToDo/v2";
    }

    @ExceptionHandler(Exception.class)
    public String handleAll(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("popupErrorMessage", e.getMessage());
        return "redirect:/ToDo/v2";
    }
}
