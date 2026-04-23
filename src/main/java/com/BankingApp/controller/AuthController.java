package com.BankingApp.controller;

import com.BankingApp.dto.LoginDTO;
import com.BankingApp.dto.UserRequestDTO;
import com.BankingApp.dto.UserResponseDTO;
import com.BankingApp.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(this.authService.register(userRequestDTO));
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        return this.authService.login(loginDTO);
    }

}
