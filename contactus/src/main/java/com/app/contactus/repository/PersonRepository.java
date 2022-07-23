package com.app.contactus.repository;

import com.app.contactus.model.Person;

import java.util.List;

public interface PersonRepository {

    public List<Person> retrieve();
    public String create(Person p);
    public String update(Person p);
    public String remove(String userId);
}