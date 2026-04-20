package com.BankingApp.service.impl;

import com.BankingApp.dto.ProductDTO;
import com.BankingApp.entity.ProductEntity;
import com.BankingApp.mapper.ProductMapper;
import com.BankingApp.repository.ProductRepository;
import com.BankingApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    public ProductDTO newProduct(String name) {

        ProductEntity p = new ProductEntity();
        p.setName(name);

        p = this.productRepository.save(p);

        return this.productMapper.toDto(p);

    }

    public ProductDTO getProduct(String id) throws Exception {
        Optional<ProductEntity> op = this.productRepository.findById(id);
        if(op.isPresent()) {
            return this.productMapper.toDto(op.get());
        } else {
            throw new Exception("prodotto non trovato");
        }

    }

    public List<ProductDTO> getProducts() {
        List<ProductEntity> list = this.productRepository.findAll();
        return this.productMapper.toDtoList(list);
    }

}
