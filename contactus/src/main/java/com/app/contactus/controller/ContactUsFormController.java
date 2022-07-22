package com.app.contactus.controller;

import com.app.contactus.model.ContactUsForm;
import com.app.contactus.service.ContactUsFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ContactUsFormController {

    @Autowired
    private ContactUsFormService contactUsFormService;

    @GetMapping("/contactus")
    public String showContactUsForm(Model model, Principal principal) {
        ContactUsForm contactUsForm = new ContactUsForm();
        model.addAttribute("contactUsForm", contactUsForm);

        return "contact-us";
    }

    @PostMapping("/savemessage")
    public String saveMessage(@Valid @ModelAttribute ContactUsForm contactUsForm, BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("contactUsForm", contactUsForm);

            return "contact-us";
        }

        contactUsFormService.save(contactUsForm);
        model.addAttribute("savedContactUsForm", contactUsForm);

        return "message-save-success";
    }
}