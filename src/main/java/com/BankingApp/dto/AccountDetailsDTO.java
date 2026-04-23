package com.BankingApp.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Data
public class AccountDetailsDTO {
    private Long id;

    private String cardNumber;
    private String cvv;
    private String iban;
    private BigDecimal balance;

    private LocalDateTime createdAt;
    private YearMonth expirationDate;

    private String type;
    private Boolean active;
}
