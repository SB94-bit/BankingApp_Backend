package com.BankingApp.mapper;

import com.BankingApp.dto.UserRequestDTO;
import com.BankingApp.entity.UserEntity;
import org.mapstruct.Mapper;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserRequestMapper{

    UserRequestDTO toDto(UserEntity pEntity);
    List<UserRequestDTO> toDtoList(List<UserEntity> pEntityList);

    UserEntity toEntity(UserRequestDTO pDto);

}
