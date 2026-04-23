package com.BankingApp.security;

import com.BankingApp.entity.UserEntity;
import com.BankingApp.exception.EmailNotFoundException;
import com.BankingApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws EmailNotFoundException {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmailIgnoreCase(email);
        if(userEntityOptional == null || userEntityOptional.isEmpty()) {
                throw new EmailNotFoundException("Email: " + email + " non trovata");
        }

        return new UserDetailsImpl(userEntityOptional.get());
    }

}
