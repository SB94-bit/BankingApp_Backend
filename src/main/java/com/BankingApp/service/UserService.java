package com.BankingApp.service;

import com.BankingApp.dto.UserDetailsDTO;
import com.BankingApp.dto.UserRequestDTO;
import com.BankingApp.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    public UserResponseDTO newUser(UserRequestDTO userRequestDTO);

    public List<UserResponseDTO> getAllUsers();

    public List<UserDetailsDTO> getAllUsersDetails();

    public UserRequestDTO getUserById(Long id);

    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO);

    public void deleteUser(UserRequestDTO userRequestDTO);

    public List<UserResponseDTO> searchByName(String name);

    public List<UserResponseDTO> searchByCodFiscale(String codFiscale);
}
