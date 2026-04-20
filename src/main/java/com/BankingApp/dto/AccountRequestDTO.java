package com.BankingApp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequestDTO {
    private Long id;
    private String iban;
    private Long userId;
}
