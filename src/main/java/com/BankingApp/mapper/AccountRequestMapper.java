package com.BankingApp.mapper;

import com.BankingApp.dto.AccountRequestDTO;
import com.BankingApp.entity.AccountEntity;
import org.mapstruct.Mapper;
import java.util.List;


@Mapper(componentModel = "spring")
public interface AccountRequestMapper {

    AccountRequestDTO toDto(AccountEntity pEntity);
    List<AccountRequestDTO> toDtoList(List<AccountEntity> pEntityList);

    AccountEntity toEntity(AccountRequestDTO pDto);
}
