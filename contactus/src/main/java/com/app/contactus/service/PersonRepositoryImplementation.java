package com.app.contactus.service;

import com.app.contactus.model.Person;
import com.app.contactus.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.SearchControls;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class PersonRepositoryImplementation implements PersonRepository {

    public static final String BASE_DN = "dc=lakshmancontactus,dc=com";

    @Autowired
    private LdapTemplate ldapTemplate;

    @Override
    public List<Person> retrieve() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.OBJECT_SCOPE);
        List<Person> people = ldapTemplate.search(query().where("objectclass").is("person"),
                new PersonAttributeMapper());
        return people;
    }

    @Override
    public String create(Person person) {
        Name dn = buildDn(person.getUserId());
        ldapTemplate.bind(dn, null, buildAttributes(person));
        System.out.println(person.getUserId() + " created successfully");
        return "Success creation of "+person.getUserId();
    }

    private Attributes buildAttributes(Person p) {
        BasicAttribute basicAttribute = new BasicAttribute("objectclass");
        basicAttribute.add("top");
        basicAttribute.add("person");
        Attributes attrs = new BasicAttributes();
        attrs.put(basicAttribute);
        attrs.put("uid", p.getUserId());
        attrs.put("cn", p.getFullName());
        attrs.put("sn", p.getLastName());
        attrs.put("userPassword", p.getPassword());
        return attrs;
    }

    public Name buildDn(String userId) {
        return LdapNameBuilder.newInstance(BASE_DN).add("ou", "people").add("uid", userId).build();
    }

    @Override
    public String update(Person p) {
        return null;
    }

    @Override
    public String remove(String userId) {
        return null;
    }

    public String retrieveByUsername(String username) {
        List<Person> people = retrieve();

        for(Person person : people) {
            if(person.getUserId().equals(username))
                return "User exist";
        }

        return "User not exist";
    }

    public String retrieveByPassword(String username, String password) {
        String userValidation = retrieveByUsername(username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        List<Person> people = retrieve();

        for(Person person : people) {
            if(person.getUserId().equals(username) && encodedPassword.equals(person.getPassword()))
                return "User exist";
        }

        return "User not exist";
    }

    private class PersonAttributeMapper implements AttributesMapper<Person> {
        @Override
        public Person mapFromAttributes(Attributes attributes) throws NamingException {
            Person person = new Person();
            person.setUserId(null != attributes.get("uid") ? attributes.get("uid").get().toString() : null);
            person.setFullName(null != attributes.get("cn") ? attributes.get("cn").get().toString() : null);
            person.setLastName(null != attributes.get("sn") ? attributes.get("sn").get().toString() : null);
            person.setPassword(
                    null != attributes.get("userPassword") ? attributes.get("userPassword").get().toString() : null);

            return person;
        }
    }
}