package model;

import exception.UnknownEntityException;

public enum UserRole {
    ADMIN(1),
    USER(2);

    private final Integer id;

    UserRole(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static UserRole getRoleById(int id){
        for (UserRole role : values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        throw new UnknownEntityException("Impossible to find UserRole");
    }

    @Override
    public String toString() {
        return "Role{" +
                "value=" + this.name() +
                "id=" + this.id +
                '}';
    }
}
