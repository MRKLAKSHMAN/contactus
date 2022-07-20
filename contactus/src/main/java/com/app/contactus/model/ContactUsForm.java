package com.app.contactus.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "messages")
public class ContactUsForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(max = 255, message = "Full Name should have maximum of 255 characters")
    @Column(name = "name")
    private String name;

    @Size(max = 255, message = "Email should have maximum of 255 characters")
    @Column(name = "email")
    private String email;

    @Size(max = 255, message = "Subject should have maximum of 255 characters")
    @Column(name = "subject")
    private String subject;

    @Column(name = "message", columnDefinition="TEXT")
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "contactedat")
    private Date contactedAt;

    public ContactUsForm() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getContactedAt() {
        return contactedAt;
    }

    public void setContactedAt(Date contactedAt) {
        this.contactedAt = contactedAt;
    }

    @Override
    public String toString() {
        return "ContactUsForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", contactedAt=" + contactedAt +
                '}';
    }
}