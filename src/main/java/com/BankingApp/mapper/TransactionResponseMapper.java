package com.BankingApp.mapper;

import com.BankingApp.dto.TransactionResponseDTO;
import com.BankingApp.entity.TransactionEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionResponseMapper {

    TransactionResponseDTO toDto(TransactionEntity pEntity);

    List<TransactionResponseDTO> toDtoList(List<TransactionEntity> pEntityList);

    TransactionEntity toEntity(TransactionResponseDTO pDto);
}