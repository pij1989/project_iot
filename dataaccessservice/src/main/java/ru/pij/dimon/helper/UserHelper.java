package ru.pij.dimon.helper;

import ru.pij.dimon.pojo.Role;
import ru.pij.dimon.pojo.User;

import java.util.HashSet;


public class UserHelper {

    public void addRole(User user, Role role){
        if(user == null || role == null){
            throw new IllegalArgumentException("User and role can not be null");
        }

        if(user.getRoles() == null) user.setRoles(new HashSet<>());
        user.getRoles().add(role);
    }
}
