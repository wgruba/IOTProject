package com.example.springboot.Seciurity;

import com.example.springboot.Mail.EmailService;
import com.example.springboot.User.User;
import com.example.springboot.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/unauthorized")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private EmailService emailService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private Map<String, String> userVerificationCodes = new HashMap<>();


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final Optional<User> userDetails = userRepository.getUserByNameOrMail(loginRequest.getUsername());
        if(userDetails.isPresent()) {
            final String jwt = jwtUtils.generateJwtToken(userDetails.get());

            String code = generateCode();
            userVerificationCodes.put(userDetails.get().getName(), code);
            emailService.sendVerificationEmail(userDetails.get().getMail(), code);

            return ResponseEntity.ok(new AuthenticationResponse(jwt, "Verification code sent"));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody VerificationRequest verificationRequest) {
        String code = userVerificationCodes.get(verificationRequest.getUsername());
        if (code != null && code.equals(verificationRequest.getCode())) {
            return ResponseEntity.ok(Collections.singletonMap("success", true));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid verification code");
        }
    }

    private String generateCode() {
        return String.valueOf(new Random().nextInt(999999));
    }
}

class LoginRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
