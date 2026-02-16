package com.expense.tracker.controller;

import com.expense.tracker.DTO.UserResponseDetailsDTO;
import com.expense.tracker.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private PersonDetailsService service;

    @GetMapping("/me")
    public UserResponseDetailsDTO getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return service.getUserByUsernameDTO(userDetails.getUsername());
    }

    @PutMapping("/salary")
    public UserResponseDetailsDTO updateSalary(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, String> payload
    ) {
        String newSalary = payload.get("salary");
        return service.updateSalary(userDetails.getUsername(), newSalary);
    }
}
