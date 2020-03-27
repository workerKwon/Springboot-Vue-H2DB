package com.example.springboot_vue_h2db.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private int age;

    private String ph;

    private boolean active;

    public User() {
    }

    public User(String name, int age, String ph){
        this.name = name;
        this.age = age;
        this.ph = ph;
        this.active = false;
    }
}
