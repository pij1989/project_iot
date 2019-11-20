package ru.pij.dimon.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.pij.dimon.dto.UserDto;
import ru.pij.dimon.service.UserService;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    private static final String REG_EX_EMAIL = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final String REG_EX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}";
    private static final int MIN_PASSWORD_LENGHT = 8;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target == null) {
            errors.reject("object.null", "User is null");
            return;
        }
        UserDto userDto = (UserDto) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "firstName.empty", "The first name is empty. Please choose a first name.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "lastName.empty", "The last name is empty. Please choose a first name.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"login","login.empty","The login is empty. Please choose a login.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email","email.empty","The email is empty. Please choose a email.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","password.empty","The password is empty. Please choose a password.");

        if(!userService.isUnique(userDto.getLogin().trim())){
            errors.rejectValue("login","login.notunique","Login "+userDto.getLogin()+" has alredy existed");
        }

        Pattern emailPattern = Pattern.compile(REG_EX_EMAIL);
        if(!emailPattern.matcher(userDto.getEmail().trim()).matches()){
            errors.rejectValue("email","email.invalid","Please enter correct a email.");
        }

        Pattern passwordPattern = Pattern.compile(REG_EX_PASSWORD);
        if(!passwordPattern.matcher(userDto.getPassword().trim()).matches()){
            errors.rejectValue("password","password.invalid","Password must contain a number and [a-z],[A-Z] character");
        }

        if (userDto.getPassword().trim().length()<MIN_PASSWORD_LENGHT){
            errors.rejectValue("password","password.minleght","Please enter a password no less 8 sign.");
        }
    }
}
