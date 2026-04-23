package com.BankingApp.service;

import com.BankingApp.dto.AccountRequestDTO;
import com.BankingApp.dto.TransactionResponseDTO;
import com.BankingApp.entity.AccountEntity;
import com.BankingApp.entity.TransactionEntity;
import com.BankingApp.mapper.TransactionResponseMapper;

import java.util.List;

public interface TransactionService {

    public void newDepositTransaction(AccountEntity accountEntity,
                                      String sourceCardNumber,
                                      String amount);

    public void newWithdrawTransaction(AccountEntity accountEntity,
                                       String amount);

    public void newTransferTransaction(AccountEntity referringAccount,
                                       AccountEntity sourceAccount,
                                       AccountEntity destinationAccount,
                                       String amount);

    public List<TransactionResponseDTO> getTransaction(AccountEntity accountEntity);
}
