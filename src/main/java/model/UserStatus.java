package model;

import exception.UnknownEntityException;

public enum UserStatus implements DbEntity {
    LOOSER(1L),
    BEGINNER(2L),
    MIDDLE(3L),
    EXPERT(4L);

    private final long id;

    UserStatus(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static UserStatus getStatusById(long id){
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
