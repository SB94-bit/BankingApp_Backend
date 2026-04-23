package com.BankingApp.dto;

import lombok.Data;
import java.time.YearMonth;

@Data
public class AccountRequestDTO {
    private Long id;
    private String iban;
    private String cardNumber;
    private String cvv;
    private YearMonth expirationDate;
    private Long userId;
}
