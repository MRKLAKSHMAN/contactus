package com.app.contactus.controller;

import com.app.contactus.model.ContactUsForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {

    @GetMapping("/contactus")
    public String showContactUsForm(Model model) {
        ContactUsForm contactUsForm = new ContactUsForm();

        model.addAttribute("contactUsForm", contactUsForm);

        return "contact-us";
    }
}
