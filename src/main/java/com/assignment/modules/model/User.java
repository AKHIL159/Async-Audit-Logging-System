
package com.assignment.modules.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.*;

@Document(collection = "users")

public class User {
    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;

    private int age;

    public User(String id, int age, String email, String name) {
        this.id = id;
        this.age = age;
        this.email = email;
        this.name = name;
    }

    public User(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
