package com.app.contactus.repository;

import com.app.contactus.model.ContactUsForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactUsFormRepository extends JpaRepository<ContactUsForm, Integer> {
}
