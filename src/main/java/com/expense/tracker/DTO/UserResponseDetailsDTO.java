package com.expense.tracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDetailsDTO {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String salary;
}
