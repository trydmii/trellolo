package ua.trydmi.trellolo.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.trydmi.trellolo.model.Role;
import ua.trydmi.trellolo.service.UserService;

import java.util.Collection;

@Service
@Slf4j
public class JwtUserService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.findByUsername(username);

        if (user == null) {
            log.info("IN loadUserByUsername user not found by username: {}", username);
            throw new UsernameNotFoundException("IN loadUserByUsername user not found by username: " + username);
        }

        Collection<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream().map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new User(user.getUsername(), user.getPassword(), authorities);
    }

}
