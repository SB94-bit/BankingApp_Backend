package com.BankingApp.mapper;

import com.BankingApp.dto.UserResponseDTO;
import com.BankingApp.entity.UserEntity;
import org.mapstruct.Mapper;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserResponseMapper{

    UserResponseDTO toDto(UserEntity pEntity);
    List<UserResponseDTO> toDtoList(List<UserEntity> pEntityList);

    UserEntity toEntity(UserResponseDTO pDto);

}
