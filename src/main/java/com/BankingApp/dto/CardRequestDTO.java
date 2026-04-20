package com.BankingApp.dto;

import lombok.Data;

@Data
public class CardRequestDTO {
    private Long id;
    private String cardNumber;
    private String cvv;
}
