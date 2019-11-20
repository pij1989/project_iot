package ru.pij.dimon.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pij.dimon.dto.UserDto;
import ru.pij.dimon.helper.UserHelper;
import ru.pij.dimon.pojo.Role;
import ru.pij.dimon.pojo.RoleName;
import ru.pij.dimon.pojo.User;
import ru.pij.dimon.repository.RoleRepository;
import ru.pij.dimon.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    private static Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private RoleRepository roleRepository;


    public boolean addUser(UserDto userDto){
        try {
            User user = new User();
            user.setFirstName(userDto.getFirstName().trim());
            user.setLastName(userDto.getLastName().trim());
            user.setLogin(userDto.getLogin().trim());
            user.setEmail(userDto.getEmail().trim());
            user.setPassword(passwordEncoder.encode(userDto.getPassword().trim()));
            if(userDto.getLogin().equals(RoleName.ADMIN.name())){
                userHelper.addRole(user,roleRepository.findRoleByName(RoleName.ADMIN));
            }else {
                userHelper.addRole(user,roleRepository.findRoleByName(RoleName.ANALITIC));
            }
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
    }

    public boolean isUnique(String login){
//      return userRepository.findAll().stream().noneMatch(user -> user.getLogin().equals(login));
        try {
            User user = userRepository.findUserByLogin(login);
            return false;
        } catch (NoSuchElementException e) {
            logger.warn(e.getMessage());
            return true;
        }
    }
}
