package com.example.engelvinmaroc.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // Changez le message d'erreur ici
        request.getSession().setAttribute("errorMessage", "Les informations de connexion fournies sont incorrectes. Assurez-vous que votre email et mot de passe sont corrects.");
        super.onAuthenticationFailure(request, response, exception);
    }
}
