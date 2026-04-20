package com.BankingApp.mapper;

import com.BankingApp.dto.CategoryDTO;
import com.BankingApp.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

//    @Mapping(target = "products", ignore = true)
    CategoryDTO toDto(CategoryEntity pEntity);

    CategoryEntity toEntity(CategoryDTO pDto);

}
