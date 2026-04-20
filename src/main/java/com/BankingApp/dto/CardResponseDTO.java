package com.BankingApp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CardResponseDTO {
    private Long id;
    private String cardNumber;
    private LocalDate expirationDate;
    private String type;
    private Boolean active;
}
