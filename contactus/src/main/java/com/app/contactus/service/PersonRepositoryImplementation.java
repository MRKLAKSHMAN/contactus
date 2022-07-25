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
import java.io.*;
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

        try {
            saveUserInLdiFile(person);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Success creation of "+person.getUserId();
    }

    private void saveUserInLdiFile(Person person) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/users.ldif",true));

        writer.newLine();
        writer.write("\ndn: " + buildDn(person.getUserId()).toString() + "\n");
        writer.write("objectclass: top\n");
        writer.write("objectclass: person\n");
        writer.write("objectclass: organizationalPerson\n");
        writer.write("objectclass: inetOrgPerson\n");
        writer.write("cn: " + person.getFullName() + "\n");
        writer.write("sn: " + person.getLastName() + "\n");
        writer.write("uid: " + person.getUserId() + "\n");
        writer.write("userPassword: " + new BCryptPasswordEncoder().encode(person.getPassword()));
        writer.close();
        System.err.println("Wrote user in ldif file successfully");
    }

    private Attributes buildAttributes(Person person) {
        BasicAttribute basicAttribute = new BasicAttribute("objectclass");
        basicAttribute.add("top");
        basicAttribute.add("person");
        basicAttribute.add("organizationalPerson");
        basicAttribute.add("inetOrgPerson");

        Attributes attributes = new BasicAttributes();
        attributes.put(basicAttribute);
        attributes.put("uid", person.getUserId());
        attributes.put("cn", person.getFullName());
        attributes.put("sn", person.getLastName());
        attributes.put("userPassword", new BCryptPasswordEncoder().encode(person.getPassword()));
        return attributes;
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

    public String retrieveByPassword(String username, String password) {
        List<Person> people = retrieve();

        for(Person person : people) {
            if(person.getUserId().equals(username))
                return "Wrong Password";
        }

        return "User not exist";
    }

    public String userExistence(String userId) {
        try {
            Person person = (Person) ldapTemplate.lookup(buildDn(userId), new PersonAttributeMapper());
            return "User exist";
        } catch (Exception exception){ }
        return "User not exist";
    }

    private class PersonAttributeMapper implements AttributesMapper<Person> {
        @Override
        public Person mapFromAttributes(Attributes attributes) throws NamingException {
            Person person = new Person();
            person.setUserId(null != attributes.get("uid") ? attributes.get("uid").get().toString() : null);

//            person.setFullName(null != attributes.get("cn") ? attributes.get("cn").get().toString() : null);
            person.setLastName(null != attributes.get("sn") ? attributes.get("sn").get().toString() : null);

            String var = attributes.get("cn").toString();
            System.out.println("var " + var);
            var = null;
            person.setFullName(var != null? attributes.get("cn").get().toString() : null);
            person.setPassword(
                    null != attributes.get("userPassword") ? attributes.get("userPassword").get().toString() : null);

            return person;
        }
    }
}