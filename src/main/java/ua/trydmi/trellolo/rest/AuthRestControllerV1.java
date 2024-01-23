package ua.trydmi.trellolo.rest;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ua.trydmi.trellolo.dto.*;
import ua.trydmi.trellolo.exception.NotAuthenticatedException;
import ua.trydmi.trellolo.exception.RefreshTokenExpiredException;
import ua.trydmi.trellolo.exception.UserNotFoundException;
import ua.trydmi.trellolo.exception.UsernameAlreadyExistsException;
import ua.trydmi.trellolo.mapper.MyMapper;
import ua.trydmi.trellolo.model.RefreshToken;
import ua.trydmi.trellolo.model.User;
import ua.trydmi.trellolo.model.UserStatus;
import ua.trydmi.trellolo.security.jwt.JwtUserService;
import ua.trydmi.trellolo.security.jwt.JwtUtility;
import ua.trydmi.trellolo.service.RefreshTokenService;
import ua.trydmi.trellolo.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestControllerV1 {

    private final JwtUtility jwtUtility;
    private final AuthenticationManager authenticationManager;
    private final JwtUserService jwtUserService;
    private final UserService userService;
    private final MyMapper mapper;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterRequestDto requestDto) {
        String username = requestDto.username();
        try {
            userService.findByUsername(username);
        } catch (UserNotFoundException e) {
            var user = mapper.requestToUser(requestDto);
            var registered = userService.register(user);
            var userDto = mapper.userToUserDto(registered);
            return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
        }
        throw new UsernameAlreadyExistsException("username already exists");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto requestDto) {
        User user;
        try {
            user = userService.findByUsername(requestDto.username());
        } catch (UserNotFoundException e) {
            throw new NotAuthenticatedException("invalid username or password");
        }
        if (user.getUserStatus().equals(UserStatus.BANNED)) {
            throw new NotAuthenticatedException("User is banned");
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.username(),
                            requestDto.password()
                    )
            );
        } catch (AuthenticationException e) {
            throw new NotAuthenticatedException("Invalid username or password");
        }

        final UserDetails userDetails
                = jwtUserService.loadUserByUsername(requestDto.username());

        final String token =
                jwtUtility.generateToken(userDetails);

        final RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return ResponseEntity.ok(new AuthResponseDto(token, refreshToken.getToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshRequestDto requestDto) {
        String refresh = requestDto.refreshToken();
        try {
            return refreshTokenService.findByToken(refresh)
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(user -> {
                        UserDetails userDetails = jwtUserService.loadUserByUsername(user.getUsername());
                        String token = jwtUtility.generateToken(userDetails);
                        return new ResponseEntity<>(new RefreshTokenResponse(token, refresh), HttpStatus.OK);
                    })
                    .orElseThrow(() -> new RefreshTokenExpiredException("refresh token not in database"));
        } catch (ExpiredJwtException e) {
            throw new RefreshTokenExpiredException("refresh token not in database");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<HttpStatus> logoutUser(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        refreshTokenService.deleteByUserId(user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
