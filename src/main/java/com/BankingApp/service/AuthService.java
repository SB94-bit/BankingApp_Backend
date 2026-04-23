package com.BankingApp.service;

import com.BankingApp.dto.LoginDTO;
import com.BankingApp.dto.UserRequestDTO;
import com.BankingApp.dto.UserResponseDTO;

public interface AuthService {

    public UserResponseDTO register(UserRequestDTO userRequestDTO);

    public String login(LoginDTO loginDTO);
}
