package com.expense.tracker.controller;

import com.expense.tracker.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.expense.tracker.DTO.PersonDataDTO;
import com.expense.tracker.service.PersonDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private PersonDetailsService personDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody PersonDataDTO person) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        person.getUsername(),
                        person.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(person.getUsername());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid Username or Password"));
    }


    @PostMapping("/signup")
    public Map<String, String> signUp(@RequestBody PersonDataDTO person) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personDetailsService.SaveUser(person);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Register Successful");

        return response;
    }


    @GetMapping("/users")
    public List<PersonDataDTO> getAllUsers() {
        return personDetailsService.getAllUsers();
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAllUsers() {
        try {
            String message = personDetailsService.deleteAllUsers();
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Failed to delete users: " + e.getMessage());
        }
    }


    @DeleteMapping("/delete/{email:.+}") 
    public String deleteUser(@PathVariable("email") String email) {
        return personDetailsService.deleteUserByEmail(email);
    }

}