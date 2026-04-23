package com.BankingApp.controller;

import com.BankingApp.dto.UserDetailsDTO;
import com.BankingApp.dto.UserRequestDTO;
import com.BankingApp.dto.UserResponseDTO;
import com.BankingApp.service.AccountService;
import com.BankingApp.service.TransactionService;
import com.BankingApp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
//###################
import com.BankingApp.dto.AccountResponseDTO;
import com.BankingApp.dto.AccountRequestDTO;
import java.math.BigDecimal;
import com.BankingApp.dto.TransactionResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/new")
    public ResponseEntity<UserResponseDTO> newUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = this.userService.newUser(userRequestDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("/allUsersDetails")
    public ResponseEntity<List<UserDetailsDTO>> getAllUsersDetails() {
        return ResponseEntity.ok(this.userService.getAllUsersDetails());
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(this.userService.updateUser(userRequestDTO));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestBody UserRequestDTO userRequestDTO) {
        this.userService.deleteUser(userRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/searchByKeyword")
    public ResponseEntity<List<UserResponseDTO>> searchByKeyword(@RequestParam String firstname, @RequestParam String lastname) {
        return ResponseEntity.ok(this.userService.findByLastNameOrFirstName(firstname, lastname));
    }
}
