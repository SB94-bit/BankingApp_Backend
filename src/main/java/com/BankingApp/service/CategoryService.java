package com.BankingApp.service;

import com.BankingApp.dto.CategoryDTO;

public interface CategoryService {

    public CategoryDTO newCategory(String name);

    public CategoryDTO getCategory(String id) throws Exception;

}
