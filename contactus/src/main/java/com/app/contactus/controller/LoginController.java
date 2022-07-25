package com.app.contactus.controller;

import com.app.contactus.service.PersonRepositoryImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private PersonRepositoryImplementation personRepositoryImplementation;

    @GetMapping("/login")
    public String loginProvider(@RequestParam(value = "logout", defaultValue = "") String logout, Model model) {
        model.addAttribute("logout", logout);
        model.addAttribute("error","");

        return "login";
    }

    @PostMapping("/loginprocessing")
    public String loginProcessing(@RequestParam("username") String username,
                                  @RequestParam("password") String password, Model model) {
        String passwordValidation = personRepositoryImplementation.retrieveByPassword(username, password);

        if(passwordValidation.equals("User not exist"))
            model.addAttribute("error", "User not exist");
        else {
            model.addAttribute("error", "Wrong Password");
            model.addAttribute("username", username);
        }

        model.addAttribute("logout", "invalid");

        return "login";
    }
}