package ru.pij.dimon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pij.dimon.dto.UserDto;
import ru.pij.dimon.service.UserService;
import ru.pij.dimon.validator.UserValidator;


@Controller
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping
    public String showSignUp(){
        return "signUp";
    }

    @PostMapping("/user")
    public String addUser(@ModelAttribute UserDto userDto,
                          BindingResult result,
                          Model model){

        userValidator.validate(userDto,result);
        if(result.hasErrors()){
            model.addAttribute("errors",result.getAllErrors());
            return "signUp";

        }

        if(!userService.addUser(userDto)){
            return "error";
        }
        return "successRegisterPage";
    }

    @PostMapping("/login")
    @ResponseBody
    public String getResultOfCheckingLogin(@RequestParam(name = "login") String login){
        return Boolean.toString(userService.isUnique(login));
    }
}
