package com.BankingApp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionResponseDTO {
    private Long id;
    private String amount;
    private String type;
    private String sourceCardNumber;
    private String destinationCardNumber;
    private LocalDateTime createdAt;

}
