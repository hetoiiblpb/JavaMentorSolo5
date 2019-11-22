package ru.hetoiiblpb.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        HttpSession session = request.getSession();
        System.out.println(authentication.getPrincipal());
        User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        session.setAttribute("user", user);
        session.setAttribute("authorities", authentication.getAuthorities());

        response.setStatus(HttpServletResponse.SC_OK);
        if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            response.sendRedirect("admin");
        } else {
            response.sendRedirect("helloUser");
        }
    }
}
