package com.BankingApp.service.impl;

import com.BankingApp.dto.CategoryDTO;
import com.BankingApp.entity.CategoryEntity;
import com.BankingApp.mapper.CategoryMapper;
import com.BankingApp.repository.CategoryRepository;
import com.BankingApp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    public CategoryDTO newCategory(String name) {

        CategoryEntity p = new CategoryEntity();
        p.setName(name);

        p = this.categoryRepository.save(p);

        return this.categoryMapper.toDto(p);

    }

    public CategoryDTO getCategory(String id) throws Exception {

        Optional<CategoryEntity> op = this.categoryRepository.findById(id);
        if(op.isPresent()) {
            return this.categoryMapper.toDto(op.get());
        } else {
            throw new Exception("prodotto non trovato");
        }

    }

}
