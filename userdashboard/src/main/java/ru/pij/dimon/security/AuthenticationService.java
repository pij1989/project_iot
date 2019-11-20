package ru.pij.dimon.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pij.dimon.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service("authService")
public class AuthenticationService implements UserDetailsService {

    private static Logger logger = LogManager.getLogger(AuthenticationService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            logger.info("loadUserByUsername() username=" + login);
            ru.pij.dimon.pojo.User appUser = userRepository.findUserByLogin(login);
            return new User(appUser.getLogin(), appUser.getPassword(),
                    appUser.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName().name()))
                            .collect(Collectors.toList()));
        } catch (NoSuchElementException e) {
            logger.warn(e.getMessage());
            throw new UsernameNotFoundException("Username " + login + " not found");
        }
    }


}
