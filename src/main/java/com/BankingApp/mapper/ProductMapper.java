package com.BankingApp.mapper;

import com.BankingApp.dto.ProductDTO;
import com.BankingApp.entity.ProductEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    ProductDTO toDto(ProductEntity pEntity);
    List<ProductDTO> toDtoList(List<ProductEntity> pEntityList);

    ProductEntity toEntity(ProductDTO pDto);

}
