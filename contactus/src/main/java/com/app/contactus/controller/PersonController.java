package com.app.contactus.controller;

import com.app.contactus.model.Person;
import com.app.contactus.service.PersonRepositoryImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PersonController {

    @Autowired
    private PersonRepositoryImplementation personRepositoryImplementation;

    @GetMapping("/usercreation")
    public String createUser(Model model) {
        Person person = new Person();
        model.addAttribute("person", person);

        return "create-user";
    }

    @PostMapping("/saveuser")
    public String saveUser(@ModelAttribute Person person, BindingResult bindingResult, Model model) {
        String userExistence = personRepositoryImplementation.retrieveByUsername(person.getUserId());

        if (userExistence.equals("User exist")) {
            model.addAttribute("userNameExist", "Username already exists. " +
                        "Please find unique name");

            return "create-user";
        }

        personRepositoryImplementation.create(person);

        model.addAttribute("userCreated", "Your account created successfully. Login Now");

        return "create-user";
    }
}
