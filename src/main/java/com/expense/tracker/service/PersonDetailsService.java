package com.expense.tracker.service;

import com.expense.tracker.DTO.PersonDataDTO;
import com.expense.tracker.DTO.UserResponseDetailsDTO;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.expense.tracker.model.PersonData;
import com.expense.tracker.repo.PersonRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonDetailsService implements UserDetailsService {

    @Autowired
    private PersonRepository repository;


    public void SaveUser(PersonDataDTO data){
        PersonData user = new PersonData();
        user.setUsername(data.getUsername());
        user.setPassword(data.getPassword());
        user.setEmail(data.getEmail());
        user.setPhoneNumber(data.getPhoneNumber());
        user.setSalary(data.getSalary());
        repository.save(user);
    }


    // Fetch User From username
    public UserResponseDetailsDTO getUserByUsernameDTO(String username) {

        PersonData user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found: " + username
                ));
        UserResponseDetailsDTO dto = new UserResponseDetailsDTO();
        dto.setId(user.getId());    
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setSalary(user.getSalary());
        return dto;
    }


    public List<PersonDataDTO> getAllUsers() {
        return repository.findAll().stream().map(user -> {
            PersonDataDTO dto = new PersonDataDTO();
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setSalary(user.getSalary());
            return dto;
        }).collect(Collectors.toList());
    }

    public String deleteUserByEmail(String email) {
        repository.deleteByEmail(email);
        return "User with email " + email + " has been deleted";
    }


    @Transactional
    public String deleteAllUsers() {
        // Runs a single query: DELETE FROM person_data;
        repository.deleteAllInBatch(); 
        return "All users have been wiped from the system, dude!";
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}