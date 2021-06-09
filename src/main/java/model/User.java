package model;

import java.util.Objects;

public class User implements DbEntity {
    private final long id;
    private final String name;
    private final UserRole role;
    private final UserStatus status;
    private final int age;
    private final String email;
    private final String passwordHash;

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

    public User(long id, String name, int age, int roleInt, String email, String password, int statusint) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.role = UserRole.getRoleById(roleInt);
        this.email = email;
        this.passwordHash = password;
        this.status = UserStatus.getStatusById(statusint);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id && age == user.age && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                '}';
    }

    @Override
    public Long getId() {
        return id;
    }
}
