package com.BankingApp.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserDetailsDTO {
    Long id;
    String firstName;
    String lastName;
    String email;
    String password;
    LocalDate dateOfBirth;
    String codFiscale;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
