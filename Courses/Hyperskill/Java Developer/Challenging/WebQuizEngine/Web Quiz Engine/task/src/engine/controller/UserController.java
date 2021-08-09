package engine.controller;

import engine.model.User;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Map;
import java.util.Set;

@RestController
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/api/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto user) {
        System.out.printf("Trying to register user (%s:%s)%n", user.getEmail(), user.getPassword());
        if (!validateEmail(user.getEmail())) {
            System.out.println("Invalid email.");
            return new ResponseEntity<>(Map.of("default_message", "must be a well-formed email address"),
                    HttpStatus.BAD_REQUEST);
        }
        try {
            userService.loadUserByUsername(user.getEmail());
            System.out.println("User already exists.");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (UsernameNotFoundException e) {
            // only register if the username doesn't exists
            User newbyUser = userService.saveUser(user.getUserOfDto(passwordEncoder));
            System.out.printf("User has been registered:%n%s%n", newbyUser);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    private boolean validateEmail(String email) {
        if (email.contains("@")) {
            String[] split = email.split("@");
            if (split.length == 2) {
                if (split[1].contains(".")) {
                    String[] domainSplit = split[1].split("\\.");
                    if (domainSplit.length == 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static class UserDto {
        @Email
        private String email;
        @Size(min = 5)
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public User getUserOfDto(BCryptPasswordEncoder passwordEncoder) {
            return new User(email, passwordEncoder.encode(password), Set.of());
        }
    }
}
