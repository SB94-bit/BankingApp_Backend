package com.BankingApp.service.impl;

import com.BankingApp.dto.TransactionResponseDTO;
import com.BankingApp.entity.AccountEntity;
import com.BankingApp.entity.TransactionEntity;
import com.BankingApp.mapper.TransactionResponseMapper;
import com.BankingApp.repository.TransactionRepository;
import com.BankingApp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionResponseMapper transactionResponseMapper;

    @Override
    public void newDepositTransaction(AccountEntity accountEntity,
                               String sourceCardNumber,
                               String amount) {
        TransactionEntity newTransaction = new TransactionEntity();
        newTransaction.setAccount(accountEntity);
        newTransaction.setSourceCardNumber(sourceCardNumber);
        newTransaction.setDestinationCardNumber(accountEntity.getCardNumber());
        newTransaction.setType("DEPOSIT");
        newTransaction.setAmount(amount);

        newTransaction.setCreatedAt(LocalDateTime.now());

        this.transactionRepository.save(newTransaction);
    }

    @Override
    public void newWithdrawTransaction(AccountEntity accountEntity,
                                      String amount) {
        TransactionEntity newTransaction = new TransactionEntity();
        newTransaction.setAccount(accountEntity);
        newTransaction.setSourceCardNumber(accountEntity.getCardNumber());
        newTransaction.setDestinationCardNumber(null);
        newTransaction.setType("WITHDRAW");
        newTransaction.setAmount(amount);

        newTransaction.setCreatedAt(LocalDateTime.now());

        this.transactionRepository.save(newTransaction);
    }

    @Override
    public void newTransferTransaction(AccountEntity referringAccount,
                                       AccountEntity sourceAccount,
                                       AccountEntity destinationAccount,
                               String amount) {
        TransactionEntity newTransaction = new TransactionEntity();
        newTransaction.setAccount(referringAccount);
        newTransaction.setSourceCardNumber(sourceAccount.getCardNumber());
        newTransaction.setDestinationCardNumber(destinationAccount.getCardNumber());
        newTransaction.setType("TRANSFER");
        newTransaction.setAmount(amount);

        newTransaction.setCreatedAt(LocalDateTime.now());

        this.transactionRepository.save(newTransaction);
    }

    @Override
    public List<TransactionResponseDTO> getTransaction(AccountEntity accountEntity){
        return transactionResponseMapper.toDtoList(this.transactionRepository.findByAccountIdOrderByIdDesc(accountEntity.getId()));
    }
}
