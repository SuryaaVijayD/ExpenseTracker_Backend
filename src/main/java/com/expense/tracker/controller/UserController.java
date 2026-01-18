package com.expense.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.expense.tracker.DTO.UserResponseDetailsDTO;
import com.expense.tracker.service.PersonDetailsService;

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
}

