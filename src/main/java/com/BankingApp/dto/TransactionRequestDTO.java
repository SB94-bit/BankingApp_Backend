package com.BankingApp.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransactionRequestDTO {
    private String sourceCardNumber;
    private String destinationCardNumber;
    private BigDecimal value;
}
