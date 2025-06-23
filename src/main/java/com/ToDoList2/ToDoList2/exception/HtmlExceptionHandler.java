package com.ToDoList2.ToDoList2.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ToDoList2.ToDoList2.controller.PageController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(assignableTypes = PageController.class)
public class HtmlExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException e, RedirectAttributes redirectAttributes) {
        log.error("[ RESOURCE_NOT_FOUND_EXCEPTION ] - An error occured: {}", e.getMessage());
        redirectAttributes.addFlashAttribute("popupErrorMessage", e.getMessage());
        return "redirect:/ToDo/v2";
    }

    @ExceptionHandler(Exception.class)
    public String handleAll(Exception e, RedirectAttributes redirectAttributes) {
        log.error("[ EXCEPTION ] - An error occured: {}", e.getMessage());
        redirectAttributes.addFlashAttribute("popupErrorMessage", e.getMessage());
        return "redirect:/ToDo/v2";
    }
}
