package id.ac.ui.cs.advprog.purchasepayment.config;

import id.ac.ui.cs.advprog.purchasepayment.dto.auth.User;
import id.ac.ui.cs.advprog.purchasepayment.dto.auth.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    WebClient webClient = WebClient.create("http://localhost:8081");

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var userResponse = webClient.get()
                    .uri("/v1/user/get-user/{username}", username)
                    .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlZHV0amllIiwiaWF0IjoxNjg0Njg4MjA4LCJleHAiOjE2ODQ2ODk2NDh9.n_jiimToeiy5Wtv16yExrwvOjMN6FV-57Mkuwux0_jg")
                    .retrieve()
                    .bodyToMono(UserResponse.class)
                    .block();
            if (userResponse == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return User.fromUserResponse(userResponse);
        };
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
