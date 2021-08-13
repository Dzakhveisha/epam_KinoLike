package Validator;

import com.epam.jwd.web.exception.DataIsNotValidateException;
import com.epam.jwd.web.model.User;

public class Validator {

    private static Validator instance;

    public static Validator getInstance() {
        Validator localInstance = instance;
        if (localInstance == null) {
            synchronized (Validator.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new Validator();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    private Validator() {
    }

    public void validateUser(User user) throws DataIsNotValidateException {
        if (user.getPasswordHash() == null || user.getEmail() == null){
            throw new DataIsNotValidateException("Null!");
        }
        if (user.getName().length() > 40 || user.getPasswordHash().length() > 40){
            throw new DataIsNotValidateException("Login or password is too long!");
        }
        if (user.getAge() > 110 && user.getAge() < 6){
            throw new DataIsNotValidateException("Age is not real!");
        }
        if (!user.getEmail().contains("@")){
            throw new DataIsNotValidateException("Email is not correct!");
        }
    }
}
