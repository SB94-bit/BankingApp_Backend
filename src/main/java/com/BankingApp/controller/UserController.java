package com.BankingApp.controller;

import com.BankingApp.dto.UserDetailsDTO;
import com.BankingApp.dto.UserRequestDTO;
import com.BankingApp.dto.UserResponseDTO;
import com.BankingApp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/isAdmin")
    public boolean isAdmin() {
        return true;
    }

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

    @GetMapping("/searchByName")
    public ResponseEntity<List<UserResponseDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(this.userService.searchByName(name));
    }
}
