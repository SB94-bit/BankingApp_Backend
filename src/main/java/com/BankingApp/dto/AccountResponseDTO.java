package com.BankingApp.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountResponseDTO {
    private Long id;
    private String iban;
    private BigDecimal balance;
    private Long userId;
    private LocalDateTime createdAt;
}
