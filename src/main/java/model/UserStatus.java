package model;

import exception.UnknownEntityException;

public enum UserStatus {
    LOOSER(1),
    BEGINNER(2),
    MIDDLE(3),
    EXPERT(4);

    private final Integer id;

    UserStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static UserStatus getStatusById(int id){
        for (UserStatus status : values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new UnknownEntityException("Impossible to find UserStatus");
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "value=" + this.name() +
                "id=" + id +
                '}';
    }
}
