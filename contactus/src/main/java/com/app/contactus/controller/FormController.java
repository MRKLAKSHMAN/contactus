package com.app.contactus.controller;

import com.app.contactus.model.ContactForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {

    @GetMapping
    public String showContactUsForm(Model model) {
        ContactForm contactForm = new ContactForm();

        model.addAttribute("contactForm", contactForm);

        return "contact-us";
    }
}
