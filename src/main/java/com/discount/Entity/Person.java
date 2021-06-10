package com.discount.Entity;

import com.discount.Dto.PersonDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persons")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @SequenceGenerator(name = "personsIdSeq", sequenceName = "persons_id_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personsIdSeq")
    private Long id;
    private String firstName;
    private String secondName;
    private String telephone;
    private String email;
    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    private String Role;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private List<Order> orders = new ArrayList<>();

    public Person() {
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public static Person from(PersonDto personDto) {
        Person person = new Person();
        person.setFirstName(personDto.getFirstName());
        person.setSecondName(personDto.getSecondName());
        person.setTelephone(personDto.getTelephone());
        person.setEmail(personDto.getEmail());
        person.setLogin(personDto.getLogin());
        person.setPassword(personDto.getPassword());
        person.setRole(personDto.getRole());
        return person;
    }

    public Person(String firstName, String secondName, String telephone, String email, String login, String password, String role) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.telephone = telephone;
        this.email = email;
        this.login = login;
        this.password = password;
        Role = role;
    }
}