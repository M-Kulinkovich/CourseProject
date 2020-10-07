package com.itlab.group3.controllers;

import com.itlab.group3.dao.model.User;
import com.itlab.group3.security.dto.AuthenticationRequestDto;
import com.itlab.group3.security.jwt.JwtTokenProvider;
import com.itlab.group3.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Api(tags = "Authentication", description = "Authentication")
@RequestMapping(produces = {"application/json"})
public class AuthenticationController {

    @NonNull
    private final AuthenticationManager authenticationManager;

    @NonNull
    private final JwtTokenProvider jwtTokenProvider;

    @NonNull
    private final UserService userService;

    @ApiOperation("Login")
    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String email = requestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
            User user = userService.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User with username: " + email + " not found"));

            String token = jwtTokenProvider.createToken(email, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", email);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }


}
