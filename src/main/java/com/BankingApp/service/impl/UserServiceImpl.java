package com.BankingApp.service.impl;

import com.BankingApp.UserRole;
import com.BankingApp.dto.UserDetailsDTO;
import com.BankingApp.dto.UserRequestDTO;
import com.BankingApp.dto.UserResponseDTO;
import com.BankingApp.entity.UserEntity;
import com.BankingApp.exception.AlreadyExistsException;
import com.BankingApp.exception.EmailNotFoundException;
import com.BankingApp.exception.ResourceNotFoundException;
import com.BankingApp.mapper.UserDetailsMapper;
import com.BankingApp.mapper.UserRequestMapper;
import com.BankingApp.mapper.UserResponseMapper;
import com.BankingApp.repository.UserRepository;
import com.BankingApp.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;
    private final UserDetailsMapper userDetailsMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    @Override
    @Transactional
    public UserResponseDTO newUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmailIgnoreCase(userRequestDTO.getEmail())) {
            throw new AlreadyExistsException("L'indirizzo email inserito non è disponibile");
        }

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
        newUserEntity.setRole(UserRole.USER);
        newUserEntity.setPassword(bCryptPasswordEncoder.encode(userRequestDTO.getPassword()));

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
    @Transactional
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) {
        if (!userRepository.existsById(userRequestDTO.getId())) {
            throw new ResourceNotFoundException("Impossibile aggiornare: utente con ID " + userRequestDTO.getId() + " non trovato");
        }
        if(userRequestDTO.getCodFiscale() == null){
            throw new ResourceNotFoundException("Campo cod Fiscale vuoto");
        }

        if(userRequestDTO.getPassword() == null){
            throw new ResourceNotFoundException("Campo password vuoto");
        }
        Optional<UserEntity> originalUser = userRepository.findById(userRequestDTO.getId());
        if(originalUser.isEmpty()){
            throw new ResourceNotFoundException("Impossibile aggiornare: utente con ID " + userRequestDTO.getId() + " non trovato");
        }
        UserEntity userEntity = userRequestMapper.toEntity(userRequestDTO);
        userEntity.setCreatedAt(originalUser.get().getCreatedAt());
        userEntity.setUpdatedAt(LocalDateTime.now());
        userEntity.setFirstName(userRequestDTO.getFirstName());
        userEntity.setLastName(userRequestDTO.getLastName());
        userEntity.setCodFiscale(userRequestDTO.getCodFiscale());
        userEntity.setRole(UserRole.USER);
        userEntity.setPassword(bCryptPasswordEncoder.encode(userRequestDTO.getPassword()));
        return userResponseMapper.toDto(userRepository.save(userEntity));
    }

    @Override
    @Transactional
    public void deleteUser(UserRequestDTO userRequestDTO) {
        if (!userRepository.existsById(userRequestDTO.getId())) {
            throw new ResourceNotFoundException("Impossibile eliminare: utente con ID " + userRequestDTO.getId() + " non trovato");
        }
        userRepository.deleteById(userRequestDTO.getId());
    }

    @Override
    public UserEntity findByEmail(String email){
        Optional<UserEntity> user = userRepository.findByEmailIgnoreCase(email);
        if(user.isEmpty()){
            throw new EmailNotFoundException("L'email: " + email + "non è corretta");
        }
        return user.get();
    }
}
