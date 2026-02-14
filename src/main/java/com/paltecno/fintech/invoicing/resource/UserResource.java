package com.paltecno.fintech.invoicing.resource;

import com.paltecno.fintech.invoicing.domain.HttpResponse;
import com.paltecno.fintech.invoicing.domain.User;
import com.paltecno.fintech.invoicing.dto.UserDTO;
import com.paltecno.fintech.invoicing.exception.ApiException;
import com.paltecno.fintech.invoicing.form.LoginForm;
import com.paltecno.fintech.invoicing.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
@Slf4j
public class UserResource {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;




    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody @Valid LoginForm loginForm){
        try{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));
        log.info("Login successful for user: {}", loginForm.getEmail());
        UserDTO user = userService.getUserByEmail(loginForm.getEmail());
        return user.isUsingMfa() ? sendVerificationCode(user) : sendResponse(user);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            throw new ApiException("An error occured. Please try again.");
        }
    }




    @PostMapping("/register")
    public ResponseEntity<HttpResponse> saveUser(@RequestBody @Valid User user){
     UserDTO userDTO = userService.createUser(user);
     return ResponseEntity.created(getUri()).body(
             HttpResponse.builder()
                     .timeStamp(now().toString())
                     .data(of("user",userDTO))
                     .message("User created")
                     .status(CREATED)
                     .statusCode(CREATED.value())
                     .build());

    }

    private URI getUri() {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/get/<userId>").toUriString());
    }

    private ResponseEntity<HttpResponse> sendResponse(UserDTO user) {
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user",user))
                        .message("Login Success")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

    private ResponseEntity<HttpResponse> sendVerificationCode(UserDTO user) {
        userService.sendVerificationCode(user);
    return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user",user))
                        .message("Verification Code Sent")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

}
