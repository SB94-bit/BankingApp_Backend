package com.BankingApp.service.impl;

import com.BankingApp.dto.*;
import com.BankingApp.entity.AccountEntity;
import com.BankingApp.entity.UserEntity;
import com.BankingApp.exception.ResourceNotFoundException;
import com.BankingApp.mapper.AccountDetailsMapper;
import com.BankingApp.mapper.AccountRequestMapper;
import com.BankingApp.mapper.AccountResponseMapper;
import com.BankingApp.repository.AccountRepository;
import com.BankingApp.service.AccountService;
import com.BankingApp.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.andreinc.mockneat.types.enums.CreditCardType;
import net.andreinc.mockneat.types.enums.IBANType;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import static net.andreinc.mockneat.unit.financial.CreditCards.creditCards;
import static net.andreinc.mockneat.unit.financial.IBANs.ibans;
import java.util.Random;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountRequestMapper accountRequestMapper;
    private final AccountResponseMapper accountResponseMapper;
    private final UserService userService;
    private final TransactionServiceImpl transactionService;
    private final AccountDetailsMapper accountDetailsMapper;

    @Override
    public AccountResponseDTO newAccount(AccountRequestDTO accountRequestDTO, String type){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserEntity newUser = userService.findByEmail(authentication.getName());

        if(newUser == null){
            throw new ResourceNotFoundException("User inesistente");
        }

        AccountEntity newAccountEntity = accountRequestMapper.toEntity(accountRequestDTO);

        newAccountEntity.setUser(newUser);
        newAccountEntity.setCardNumber(creditCards().type(CreditCardType.VISA_16).get());
        newAccountEntity.setCvv(Integer.toString(new Random().nextInt(900) + 100));
        newAccountEntity.setBalance(BigDecimal.ZERO);
        newAccountEntity.setCreatedAt((LocalDateTime.now()));
        newAccountEntity.setExpirationDate(YearMonth.from(LocalDate.now()).plusYears(5));
        newAccountEntity.setActive(true);

        if(type == null){
            new ResourceNotFoundException("Il campo tipo è assente");
        }
        if(type.equalsIgnoreCase("DEBIT")){
            newAccountEntity.setType("DEBIT");
            newAccountEntity.setIban(ibans().type(IBANType.ITALY).get());
        } else if(type.equalsIgnoreCase("PREPAID")){
            newAccountEntity.setType("PREPAID");
            newAccountEntity.setIban(null);
        } else {
            new ResourceNotFoundException("Il campo tipo non è corretto");
        }

        return accountResponseMapper.toDto(this.accountRepository.save(newAccountEntity));
    }

    @Override
    @Transactional
    public void disableAccount(String cardNumber){
        AccountEntity accountEntity = accountRepository.findByCardNumber(cardNumber);
        if(accountEntity == null){
            throw new ResourceNotFoundException("Numero di carta non trovato");
        }
        accountEntity.setActive(false);
    }

    @Override
    @Transactional
    public void enableAccount(String cardNumber){
        AccountEntity accountEntity = accountRepository.findByCardNumber(cardNumber);
        if(accountEntity == null){
            throw new ResourceNotFoundException("Numero di carta non trovato");
        }
        accountEntity.setActive(true);
    }

    @Override
    @Transactional
    public AccountResponseDTO deposit(TransactionRequestDTO transactionRequestDTO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserEntity user = userService.findByEmail(authentication.getName());

        AccountEntity accountEntity = accountRepository.findByCardNumber(transactionRequestDTO.getDestinationCardNumber());
        if(accountEntity == null){
            throw new ResourceNotFoundException("Numero di carta non corretto");
        }

        if(!user.getId().equals(accountEntity.getUser().getId())){
            throw new ResourceNotFoundException("Attenzione non esiste nessuna carta con numero " +
                    transactionRequestDTO.getDestinationCardNumber()+
                    " associata a questo user");
        }

        accountEntity = accountRepository
                .findByIdWithLock(accountEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account non trovato"));


        if(accountEntity.getActive() == false){
            throw new ResourceNotFoundException("L'account non è attivo");
        }
        if (accountEntity.getBalance() == null || transactionRequestDTO.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResourceNotFoundException("L'importo del deposito deve essere maggiore di zero");
        }

        accountEntity.add(transactionRequestDTO.getValue());
        String amount = "+".concat(transactionRequestDTO.getValue().toString());
        transactionService.newDepositTransaction(accountEntity, null, amount);
        return accountResponseMapper.toDto(accountEntity);
    }

    @Override
    @Transactional
    public AccountResponseDTO withdraw(TransactionRequestDTO transactionRequestDTO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserEntity user = userService.findByEmail(authentication.getName());

        AccountEntity accountEntity = accountRepository.findByCardNumber(transactionRequestDTO.getSourceCardNumber());
        if(accountEntity == null){
            throw new ResourceNotFoundException("Numero di carta non corretto");
        }

        if(!user.getId().equals(accountEntity.getUser().getId())){
            throw new ResourceNotFoundException("Attenzione non esiste nessuna carta con numero " +
                    transactionRequestDTO.getSourceCardNumber()+
                    " associata a questo user");
        }

        accountEntity = accountRepository
                .findByIdWithLock(accountEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account non trovato"));

        if(accountEntity.getActive() == false){
            throw new ResourceNotFoundException("L'account non è attivo");
        }
        if (transactionRequestDTO.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResourceNotFoundException("L'importo del prelievo deve essere maggiore di zero");
        }
        if (accountEntity.getBalance() == null || accountEntity.getBalance().compareTo(transactionRequestDTO.getValue()) < 0) {
            throw new ResourceNotFoundException("Fondi non sufficienti per effettuare il prelievo");
        }

        accountEntity.subtract(transactionRequestDTO.getValue());
        String amount = "-".concat(transactionRequestDTO.getValue().toString());
        transactionService.newWithdrawTransaction(accountEntity, amount);
        return accountResponseMapper.toDto(accountEntity);
    }

    @Override
    @Transactional
    public AccountResponseDTO transfer(TransactionRequestDTO transactionRequestDTO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserEntity user = userService.findByEmail(authentication.getName());

        AccountEntity sourceAccountEntity = accountRepository.findByCardNumber(transactionRequestDTO.getSourceCardNumber());
        if(sourceAccountEntity == null){
            throw new ResourceNotFoundException("Numero di carta non corretto");
        }

        if(!user.getId().equals(sourceAccountEntity.getUser().getId())){
            throw new ResourceNotFoundException("Attenzione non esiste nessuna carta con numero " +
                    transactionRequestDTO.getSourceCardNumber()+
                    " associata a questo user");
        }

        sourceAccountEntity = accountRepository
                .findByIdWithLock(sourceAccountEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account non trovato"));

        if(sourceAccountEntity.getActive() == false){
            throw new ResourceNotFoundException("L'account non è attivo");
        }
        if (transactionRequestDTO.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResourceNotFoundException("L'importo del trasferimento deve essere maggiore di zero");
        }

        if (sourceAccountEntity.getBalance() == null || sourceAccountEntity.getBalance().compareTo(transactionRequestDTO.getValue()) < 0) {
            throw new ResourceNotFoundException("Fondi non sufficienti per effettuare il trasferimento");
        }

        AccountEntity destinationAccountEntity = accountRepository.findByCardNumber(transactionRequestDTO.getDestinationCardNumber());

        if(destinationAccountEntity == null){
            throw new ResourceNotFoundException("Numero di carta del beneficiario non corretto");
        }

        destinationAccountEntity = accountRepository
                .findByIdWithLock(destinationAccountEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account di destinazione non trovato"));

        if(destinationAccountEntity.getActive() == false){
            throw new ResourceNotFoundException("L'account di destinazione non è attivo");
        }

        sourceAccountEntity.subtract(transactionRequestDTO.getValue());
        String sourceAmount = "-".concat(transactionRequestDTO.getValue().toString());
        transactionService.newTransferTransaction(sourceAccountEntity,sourceAccountEntity,destinationAccountEntity,sourceAmount);

        destinationAccountEntity.add(transactionRequestDTO.getValue());
        String destinationAmount = "+".concat(transactionRequestDTO.getValue().toString());
        transactionService.newTransferTransaction(destinationAccountEntity,sourceAccountEntity,destinationAccountEntity,destinationAmount);
        return accountResponseMapper.toDto(sourceAccountEntity);
    }

    @Override
    public List<TransactionResponseDTO> getTransactionList(String cardNumber){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserEntity user = userService.findByEmail(authentication.getName());

        AccountEntity accountEntity = accountRepository.findByCardNumber(cardNumber);
        if(accountEntity == null){
            throw new ResourceNotFoundException("Numero di carta non corretto");
        }

        if(!user.getId().equals(accountEntity.getUser().getId())){
            throw new ResourceNotFoundException("Attenzione non esiste nessuna carta con numero " +
                    cardNumber+
                    " associata a questo user");
        }
        return transactionService.getTransaction(accountEntity);
    }

    @Override
    public AccountDetailsDTO showDetails(String cardNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        UserEntity user = userService.findByEmail(authentication.getName());

        AccountEntity accountEntity = accountRepository.findByCardNumber(cardNumber);
        if (accountEntity == null) {
            throw new ResourceNotFoundException("Numero di carta non corretto");
        }

        if (!user.getId().equals(accountEntity.getUser().getId())) {
            throw new ResourceNotFoundException("Attenzione non esiste nessuna carta con numero " +
                    cardNumber +
                    " associata a questo user");
        }
        return accountDetailsMapper.toDto(accountEntity);
    }
}
