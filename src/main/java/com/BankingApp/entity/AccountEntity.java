package com.BankingApp.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Entity
@Data
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cardNumber;

    private String cvv;

    private String iban;

    private BigDecimal balance;

    private LocalDateTime createdAt;
    private YearMonth expirationDate;

    private String type;
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<TransactionEntity> transactions;

    public void add(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void subtract(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }
}
