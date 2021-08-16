package com.epam.jwd.web.model;

import com.epam.jwd.web.exception.UnknownEntityException;

import java.util.Objects;

public class User implements DbEntity {
    private long id;
    private final String name;
    private final String passwordHash;
    private UserRole role;
    private UserStatus status;
    private int age;
    private String email;

    public User(long id, String name, int age, long roleInt, String email, String password, long statusInt) {
        this.id = id;
        this.name = name;
        this.age = age;
        try {
            this.role = UserRole.getRoleById(roleInt);
        } catch (UnknownEntityException e) {
            this.role = UserRole.USER;
        }
        this.email = email;
        this.passwordHash = password;
        try {
            this.status = UserStatus.getStatusById(statusInt);
        } catch (UnknownEntityException e) {
            this.status = UserStatus.BEGINNER;
        }
    }

    public User(String login, String password) {
        this(login,password, UserRole.USER);
    }

    public User(String login, String password, UserRole role){
        this.name = login;
        this.passwordHash = password;
        this.role = role;
        this.status = UserStatus.BEGINNER;
    }

    public User(String name, String password, String mail, Integer age) {
        this.name = name;
        this.age = age;
        this.role = UserRole.USER;
        this.email = mail;
        this.passwordHash = password;
        this.status = UserStatus.BEGINNER;
    }

    public User(String name, int age, Long roleInt, String email, String password, Long statusInt) {
        this.id = id;
        this.name = name;
        this.age = age;
        try {
            this.role = UserRole.getRoleById(roleInt);
        } catch (UnknownEntityException e) {
            this.role = UserRole.USER;
        }
        this.email = email;
        this.passwordHash = password;
        try {
            this.status = UserStatus.getStatusById(statusInt);
        } catch (UnknownEntityException e) {
            this.status = UserStatus.BEGINNER;
        }
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id && age == user.age && Objects.equals(name, user.name) && Objects.equals(passwordHash, user.passwordHash) && role == user.role && status == user.status && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, passwordHash, role, status, age, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public Long getId() {
        return id;
    }
}
