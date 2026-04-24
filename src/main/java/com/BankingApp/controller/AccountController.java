package com.BankingApp.controller;

import com.BankingApp.dto.*;

import java.util.List;

import com.BankingApp.service.AccountService;
import com.BankingApp.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    @PostMapping("/new")
    public ResponseEntity<AccountResponseDTO> newAccount(@Valid @RequestBody AccountRequestDTO accountRequestDTO, String type) {
        AccountResponseDTO accountResponseDTO = this.accountService.newAccount(accountRequestDTO, type);
        return new ResponseEntity<>(accountResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/disableAccount")
    public ResponseEntity<Void> disableAccount(@RequestParam String cardNumber){
        this.accountService.disableAccount(cardNumber);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/enableAccount")
    public ResponseEntity<Void> enableAccount(@RequestParam String cardNumber){
        this.accountService.enableAccount(cardNumber);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountResponseDTO> deposit(@RequestBody TransactionRequestDTO transactionRequestDTO){
        AccountResponseDTO accountResponseDTO = this.accountService.deposit(transactionRequestDTO);
        return new ResponseEntity<>(accountResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountResponseDTO> withdraw(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        AccountResponseDTO accountResponseDTO = this.accountService.withdraw(transactionRequestDTO);
        return new ResponseEntity<>(accountResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<AccountResponseDTO> transfer(@RequestBody TransactionRequestDTO transactionRequestDTO){
        AccountResponseDTO accountResponseDTO = this.accountService.transfer(transactionRequestDTO);
        return new ResponseEntity<>(accountResponseDTO, HttpStatus.OK);
    }
    @GetMapping("/accountDetails")
    public ResponseEntity<AccountDetailsDTO> showDetails(@RequestParam String cardNumber){
        return ResponseEntity.ok(this.accountService.showDetails(cardNumber));
    }

    @GetMapping("/transactionList")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsById(@RequestParam String cardNumber){
        return ResponseEntity.ok(this.accountService.getTransactionList(cardNumber));
    }
}
