package com.BankingApp.mapper;

import com.BankingApp.dto.AccountDetailsDTO;
import com.BankingApp.entity.AccountEntity;
import org.mapstruct.Mapper;
import java.util.List;


@Mapper(componentModel = "spring")
public interface AccountDetailsMapper {

    AccountDetailsDTO toDto(AccountEntity pEntity);
    List<AccountDetailsDTO> toDtoList(List<AccountEntity> pEntityList);

    AccountEntity toEntity(AccountDetailsDTO pDto);
}
