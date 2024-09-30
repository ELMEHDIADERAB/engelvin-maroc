package com.example.engelvinmaroc.Security;//package com.example.engelvinmaroc.Security;
//
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
//@AllArgsConstructor
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    private final UserDetailsService userDetailsService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .formLogin()
//                .loginPage("/login") // Specify the URL of your custom login page
//                .defaultSuccessUrl("/dashboard", true) // Redirect to dashboard on successful login
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout") // Redirect to login page on logout
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .permitAll()
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/assets/**", "/images/**", "/css/**", "/", "/login/**").permitAll() // Allow public access to these URLs
//                .anyRequest().authenticated() // Require authentication for any other request
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/denied") // Redirect to access denied page
//                .and()
//                .userDetailsService(userDetailsService);
//
//        return httpSecurity.build();
//    }
//}


import com.example.engelvinmaroc.Security.CustomAuthenticationFailureHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// Autres imports...

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final UserDetailsService userDetailsService;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin()
                .loginPage("/login") // Spécifie l'URL de ta page de connexion personnalisée
                .defaultSuccessUrl("/dashboard", true) // Redirige vers le tableau de bord en cas de succès
                .failureHandler(customAuthenticationFailureHandler) // Utilise le gestionnaire d'échec personnalisé
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout") // Redirige vers la page de connexion après la déconnexion
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/pwd","/forgot-password", "/reset-password","/resetpassword","/assets/**", "/images/**", "/css/**", "/", "/login/**").permitAll() // Accès public à ces URL
                .anyRequest().authenticated() // Exige l'authentification pour toute autre demande
                .and()
                .exceptionHandling()
                .accessDeniedPage("/denied") // Redirige vers la page d'accès refusé
                .and()
                .userDetailsService(userDetailsService);

        return httpSecurity.build();
    }
}




//package com.example.engelvinmaroc.Security;
//
//import lombok.AllArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true)
//@AllArgsConstructor
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    private final UserDetailsService userDetailsService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .formLogin()
//                .loginPage("/login") // Specify the URL of your custom login page
//                .defaultSuccessUrl("/dashboard", true) // Redirect to dashboard on successful login
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout") // Redirect to login page on logout
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .permitAll()
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/assets/**", "/images/**", "/css/**", "/", "/login/**").permitAll() // Allow public access to these URLs
//                .anyRequest().authenticated() // Require authentication for any other request
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/denied") // Redirect to access denied page
//                .and()
//                .userDetailsService(userDetailsService);
//
//        return httpSecurity.build();
//    }
//}
//
//
//
//
//
//
//
//
////package com.example.engelvinmaroc.Security;
////
////import lombok.AllArgsConstructor;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
////import org.springframework.security.core.userdetails.UserDetailsService;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.web.SecurityFilterChain;
////
////@Configuration
////@EnableWebSecurity
////@EnableMethodSecurity(prePostEnabled = true)
////@AllArgsConstructor
////public class SecurityConfig {
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
////    private UserDetailsService  userDetailsService;
////
////
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
////        httpSecurity
////                .formLogin()
////                .loginPage("/login") // Specify the URL of your custom login page
////                .defaultSuccessUrl("/dashboard", true) // Redirect to dashboard on successful login
////                .permitAll()
////                .and()
////                .logout()
////                .logoutUrl("/logout")
////                .logoutSuccessUrl("/login?logout") // Redirect to login page on logout
////                .invalidateHttpSession(true)
////                .deleteCookies("JSESSIONID")
////                .permitAll()
////                .and()
////                .authorizeHttpRequests()
////                .requestMatchers("/assets/**", "/images/**", "/css/**", "/", "/login/**").permitAll() // Allow public access to these URLs
////                .anyRequest().authenticated() // Require authentication for any other request
////                .and()
////                .exceptionHandling()
////                .accessDeniedPage("/denied")
////                //.accessDeniedHandler("/denied")
////                .and()
////                .userDetailsService(userDetailsService);
////
//////                .formLogin()
//////                .loginPage("/login") // Specify the URL of your custom login page
//////                .defaultSuccessUrl("/dashboard", true) // Redirect to dashboard on successful login
//////                .permitAll()
//////                .and()
//////                .logout()
//////                .logoutUrl("/logout")
//////                .logoutSuccessUrl("/login?logout") // Redirect to login page on logout
//////                .invalidateHttpSession(true)
//////                .deleteCookies("JSESSIONID")
//////                .permitAll()
//////                .and()
//////                .authorizeHttpRequests()
//////                .requestMatchers("/assets/**", "/images/**", "/css/**", "/", "/login/**").permitAll() // Allow public access to these URLs
//////                .anyRequest().authenticated() // Require authentication for any other request
//////                .and()
//////                .exceptionHandling()
//////                .accessDeniedPage("/denied"); // Redirect to access denied page
////
////        return httpSecurity.build();
////    }
////
////}
