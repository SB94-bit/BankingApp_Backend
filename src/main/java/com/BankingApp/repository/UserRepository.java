package com.BankingApp.repository;

import com.BankingApp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByFirstNameIgnoreCase(String firstName);

    List<UserEntity> findByCodFiscaleIgnoreCase(String codFiscale);

    boolean existsByCodFiscaleIgnoreCase(String codFiscale);
}
