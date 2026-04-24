package com.BankingApp.service;

import com.BankingApp.dto.*;
import com.BankingApp.entity.AccountEntity;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    public AccountResponseDTO newAccount(AccountRequestDTO accountRequestDTO, String type);

    public void disableAccount(String cardNumber);

    public void enableAccount(String cardNumber);

    public AccountResponseDTO deposit(TransactionRequestDTO transactionRequestDTO);

    public AccountResponseDTO withdraw(TransactionRequestDTO transactionRequestDTO);

    public AccountResponseDTO transfer(TransactionRequestDTO transactionRequestDTO);

    public List<TransactionResponseDTO> getTransactionList(String cardNumber);

    public AccountDetailsDTO showDetails(String cardNumber);


}
