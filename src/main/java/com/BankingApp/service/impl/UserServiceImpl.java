package com.BankingApp.service.impl;

import com.BankingApp.dto.UserDetailsDTO;
import com.BankingApp.dto.UserRequestDTO;
import com.BankingApp.dto.UserResponseDTO;
import com.BankingApp.entity.UserEntity;
import com.BankingApp.exception.AlreadyExistsException;
import com.BankingApp.exception.ResourceNotFoundException;
import com.BankingApp.mapper.UserDetailsMapper;
import com.BankingApp.mapper.UserRequestMapper;
import com.BankingApp.mapper.UserResponseMapper;
import com.BankingApp.repository.UserRepository;
import com.BankingApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;
    private final UserDetailsMapper userDetailsMapper;

    @Override
    public UserResponseDTO newUser(UserRequestDTO userRequestDTO) {
        if(userRequestDTO.getCodFiscale() == null){
            throw new ResourceNotFoundException("Campo cod Fiscale vuoto");
        }
        if (userRepository.existsByCodFiscaleIgnoreCase(userRequestDTO.getCodFiscale())) {
            throw new AlreadyExistsException("Esiste già un utente con questo Codice Fiscale: " + userRequestDTO.getCodFiscale());
        }
        if(userRequestDTO.getPassword() == null){
            throw new ResourceNotFoundException("Campo password vuoto");
        }

        UserEntity newUserEntity = userRequestMapper.toEntity(userRequestDTO);

        newUserEntity.setCreatedAt(LocalDateTime.now());
        newUserEntity.setUpdatedAt(LocalDateTime.now());
        newUserEntity.setCodFiscale(userRequestDTO.getCodFiscale());

        return userResponseMapper.toDto(userRepository.save(newUserEntity));
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userResponseMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public List<UserDetailsDTO> getAllUsersDetails() {
        return userDetailsMapper.toDtoList(userRepository.findAll());
    }

    @Override
    public UserRequestDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userRequestMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Utente con ID " + id + " non trovato"));
    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) {
        if (!userRepository.existsById(userRequestDTO.getId())) {
            throw new ResourceNotFoundException("Impossibile aggiornare: utente con ID " + userRequestDTO.getId() + " non trovato");
        }
        UserEntity userEntity = userRequestMapper.toEntity(userRequestDTO);
        userEntity.setUpdatedAt(LocalDateTime.now());
        return userResponseMapper.toDto(userRepository.save(userEntity));
    }

    @Override
    public void deleteUser(UserRequestDTO userRequestDTO) {
        if (!userRepository.existsById(userRequestDTO.getId())) {
            throw new ResourceNotFoundException("Impossibile eliminare: utente con ID " + userRequestDTO.getId() + " non trovato");
        }
        userRepository.deleteById(userRequestDTO.getId());
    }

    @Override
    public List<UserResponseDTO> searchByName(String name) {
        return userRepository.findByFirstNameIgnoreCase(name).stream()
                .map(userResponseMapper::toDto)
                .toList();
    }

    @Override
    public List<UserResponseDTO> searchByCodFiscale(String codFiscale){
        return userRepository.findByCodFiscaleIgnoreCase(codFiscale).stream()
                .map(userResponseMapper::toDto)
                .toList();
    }
}
