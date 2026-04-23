package com.BankingApp.service.impl;

import com.BankingApp.dto.LoginDTO;
import com.BankingApp.dto.UserRequestDTO;
import com.BankingApp.dto.UserResponseDTO;
import com.BankingApp.entity.UserEntity;
import com.BankingApp.exception.AlreadyExistsException;
import com.BankingApp.security.jwt.JwtService;
import com.BankingApp.service.AuthService;
import com.BankingApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public UserResponseDTO register(UserRequestDTO userRequestDTO){
        return userService.newUser(userRequestDTO);
    }

    public String login(LoginDTO loginDTO){

        UserEntity user = userService.findByEmail(loginDTO.getEmail());
        if(user == null){
            throw new AlreadyExistsException("L'email selezionata non è disponibile");
        }
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateJwtToken(loginDTO.getEmail());
        }
        return "request denied";
    }
}
