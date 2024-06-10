package com.example.springboot.Seciurity;

import com.example.springboot.Mail.EmailService;
import com.example.springboot.User.User;
import com.example.springboot.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private Map<String, String> userVerificationCodes = new HashMap<>();

    @PostMapping("unauthorized/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login attempt for user: " + loginRequest.getUsername());
        try {
            Optional<User> userDetails = userRepository.getUserByNameOrMail(loginRequest.getUsername());
            if (userDetails.isPresent()) {
                User user = userDetails.get();
                if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                    String code = generateCode();
                    userVerificationCodes.put(user.getName(), code);
                    emailService.sendVerificationEmail(user.getMail(), code);
                    return ResponseEntity.ok(Collections.singletonMap("message", "Verification code sent"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("unauthorized/verify")
    public ResponseEntity<?> verify(@RequestBody VerificationRequest verificationRequest) {
        String code = userVerificationCodes.get(verificationRequest.getUsername());
        if (code != null && code.equals(verificationRequest.getCode())) {
            Optional<User> userDetails = userRepository.getUserByNameOrMail(verificationRequest.getUsername());
            if (userDetails.isPresent()) {
                User user = userDetails.get();
                final String jwt = jwtUtils.generateJwtToken(user);
                return ResponseEntity.ok(new AuthResponse(jwt));            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid verification code");
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


