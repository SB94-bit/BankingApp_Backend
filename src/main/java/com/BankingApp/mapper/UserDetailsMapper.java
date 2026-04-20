package com.BankingApp.mapper;

import com.BankingApp.dto.UserDetailsDTO;
import com.BankingApp.entity.UserEntity;
import org.mapstruct.Mapper;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserDetailsMapper{

    UserDetailsDTO toDto(UserEntity pEntity);
    List<UserDetailsDTO> toDtoList(List<UserEntity> pEntityList);

    UserEntity toEntity(UserDetailsDTO pDto);

}
