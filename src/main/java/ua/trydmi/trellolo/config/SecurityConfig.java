package ua.trydmi.trellolo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.trydmi.trellolo.security.jwt.JwtFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
    private static final String MODERATOR_ENDPOINT = "/api/v1/moder/**";
    private static final String USER_ENDPOINT = "/api/v1/**";
    private static final String AUTH_ENDPOINT = "/api/v1/auth/**";
    private static final String[] SWAGGER_WHITELIST = {

    };
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String MODERATOR_ROLE = "MODERATOR";
    private static final String USER_ROLE = "USER";

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_ENDPOINT).permitAll()
                .antMatchers(SWAGGER_WHITELIST).permitAll()
                .antMatchers(MODERATOR_ENDPOINT).hasAnyRole(MODERATOR_ROLE, ADMIN_ROLE)
                .antMatchers(ADMIN_ENDPOINT).hasRole(ADMIN_ROLE)
                .antMatchers(USER_ENDPOINT).hasAnyRole(USER_ROLE, ADMIN_ROLE, MODERATOR_ROLE)
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();

    }

}
