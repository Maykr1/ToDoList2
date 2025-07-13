package com.ToDoList2.ToDoList2.controller;

import java.util.List;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;

import com.ToDoList2.ToDoList2.entity.CustomUser;
import com.ToDoList2.ToDoList2.entity.ToDo;
import com.ToDoList2.ToDoList2.exception.ResourceNotFoundException;
import com.ToDoList2.ToDoList2.exception.UserAlreadyExistsException;
import com.ToDoList2.ToDoList2.service.CustomUserService;
import com.ToDoList2.ToDoList2.service.ToDoService;

@Controller
@RequiredArgsConstructor
public class PageController implements ErrorController {
    private final ToDoService toDoService;
    private final CustomUserService customUserService;

    @GetMapping("")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<ToDo> toDoList = this.toDoService.getToDosByUsername(userDetails.getUsername());

        model.addAttribute("toDoList", toDoList);
        model.addAttribute("username", userDetails.getUsername());

        return "index";
    }

    @GetMapping("/add-item")
    public String addItem(Model model) {
        model.addAttribute("mode", "Add");
        model.addAttribute("todo", new ToDo());
        
        return "add-edit-item";
    }

    @PostMapping("/todos")
    public String createToDo(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute("todo") @Valid ToDo toDo, BindingResult result, Model model) {
        String mode = (toDo.getId() == null) ? "Add" : "Edit";
        
        if (result.hasErrors()) {
            model.addAttribute("mode", mode);
            return "add-edit-item";
        }

        try {
            toDo.setCustomUser(customUserService.getCustomUserByUsername(userDetails.getUsername()));
            toDoService.createToDo(toDo);
        } catch (ResourceNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("mode", mode);
            return "add-edit-item";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Something went wrong, please try again.");
            model.addAttribute("mode", mode);
            return "add-edit-item";
        }
        
        return "redirect:/";
    }

    @GetMapping("/edit-item/{id}")
    public String editItem(@PathVariable("id") Integer id, Model model) {
        ToDo selectedItem = toDoService.getToDoById(id);
        
        model.addAttribute("mode", "Edit");
        model.addAttribute("todo", selectedItem);
        return "add-edit-item";
    }

    @PostMapping("/delete-item/{id}")
    public String deleteItem(@PathVariable("id") Integer id) {
        toDoService.deleteToDo(id);

        return "redirect:/";
    }

    @PostMapping("/clear")
    public String deleteAllItems() {
        toDoService.deleteAllToDos();
        return "redirect:/";
    }

    @GetMapping("/createnewuser")
    public String createNewUser(Model model) {
        model.addAttribute("newuser", new CustomUser());

        return "create-new-user";
    }
    
    @PostMapping("/createcustomuser")
    public String createCustomUser(@ModelAttribute("newuser") @Valid CustomUser customUser, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "create-new-user";
        }

        try {
            customUserService.createUser(customUser);
        } catch (UserAlreadyExistsException e) {
            result.rejectValue("username", "error.customUser", e.getMessage());
            return "create-new-user";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Something went wrong, please try again.");
            return "create-new-user";
        }

        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500";
            }
        }

        return "error";
    }

    @GetMapping("/back-to-portfolio")
    public String backToPortfolio() {
        return "redirect://ethansclark.com";
    }
}
