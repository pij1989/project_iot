package ru.pij.dimon.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        if(authentication.getPrincipal() instanceof User){
            User user = (User) authentication.getPrincipal();
            Cookie cookie = new Cookie("login",user.getUsername());
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
