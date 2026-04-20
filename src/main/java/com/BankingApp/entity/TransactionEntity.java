package com.BankingApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private  String type;
    private  String status;

    @Column(nullable = false)
    private  Long sourceAccountId;

    @Column(nullable = false)
    private  Long destinationAccountId;


    private  String description;
    private LocalDateTime createdAt;
}
