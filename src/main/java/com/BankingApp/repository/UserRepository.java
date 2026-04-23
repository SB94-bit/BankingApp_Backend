package com.BankingApp.repository;

import com.BankingApp.entity.AccountEntity;
import com.BankingApp.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByLastNameOrFirstNameIgnoreCase(String firstName, String lastname);

    List<UserEntity> findByCodFiscaleIgnoreCase(String codFiscale);

    boolean existsByCodFiscaleIgnoreCase(String codFiscale);

    Optional<UserEntity> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}
