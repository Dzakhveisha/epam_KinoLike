package model;

import exception.UnknownEntityException;

public enum UserRole implements DbEntity {
    ADMIN(1L),
    USER(2L);

    private final long id;

    UserRole(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static UserRole getRoleById(long id){
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
