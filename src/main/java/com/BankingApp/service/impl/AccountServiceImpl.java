package com.BankingApp.service.impl;


import com.BankingApp.dto.AccountRequestDTO;
import com.BankingApp.dto.AccountResponseDTO;
import com.BankingApp.entity.AccountEntity;
import com.BankingApp.entity.UserEntity;
import com.BankingApp.mapper.AccountRequestMapper;
import com.BankingApp.mapper.AccountResponseMapper;
import com.BankingApp.mapper.UserRequestMapper;
import com.BankingApp.repository.AccountRepository;
import com.BankingApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountServiceImpl {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRequestMapper accountRequestMapper;

    @Autowired
    private AccountResponseMapper accountResponseMapper;

    @Autowired
    private UserRequestMapper userRequestMapper;

    private UserServiceImpl userService;

    public AccountResponseDTO newAccount(AccountRequestDTO accountRequestDTO){
        UserEntity newUser = userRequestMapper
                .toEntity(userService.getUserById(accountRequestDTO.getUserId()));

        AccountEntity newAccountEntity = accountRequestMapper.toEntity(accountRequestDTO);
        newAccountEntity.setCreatedAt((LocalDateTime.now()));
        return accountResponseMapper.toDto(this.accountRepository.save(newAccountEntity));
    }

    //public List<AccountResponseDTO> getAllAccounts();
    //public AccountResponseDTO getAccountById();
}
