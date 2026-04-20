package com.BankingApp.mapper;

import com.BankingApp.dto.AccountResponseDTO;
import com.BankingApp.entity.AccountEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountResponseMapper {

    AccountResponseDTO toDto(AccountEntity pEntity);
    List<AccountResponseDTO> toDtoList(List<AccountEntity> pEntityList);

    AccountEntity toEntity(AccountResponseDTO pDto);
}
