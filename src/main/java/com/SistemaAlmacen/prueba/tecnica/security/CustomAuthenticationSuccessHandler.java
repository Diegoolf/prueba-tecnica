package com.SistemaAlmacen.prueba.tecnica.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
        HttpServletResponse response,
        Authentication authentication) throws IOException {
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        String redirectUrl = "/default";
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMINISTRADOR")) {
                redirectUrl = "/admin/inicio";
                break;
            } else if (authority.getAuthority().equals("ROLE_ALMACENISTA")) {
                redirectUrl = "/almacen/inicio";
                break;
            }
        }
        
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
