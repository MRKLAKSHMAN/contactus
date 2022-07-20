package com.app.contactus.service;

import com.app.contactus.model.ContactUsForm;
import com.app.contactus.repository.ContactUsFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ContactUsFormService {

    @Autowired
    private ContactUsFormRepository contactUsFormRepository;

    public ContactUsForm save(ContactUsForm contactUsForm) {
        contactUsForm.setContactedAt(getFormattedDate(new Date(), "dd/MM/yyyy  HH:mm:ss"));

        contactUsFormRepository.save(contactUsForm);

        return contactUsForm;
    }

    private Date getFormattedDate(Date date, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        String formattedDate = formatter.format(date);
        try {
            date = formatter.parse(formattedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return date;
    }
}