package com.BankingApp.dto;

import lombok.Data;

@Data
public class ProductDTO {

    String id;
    String name;
    CategoryDTO category;
}
