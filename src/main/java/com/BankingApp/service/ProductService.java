package com.BankingApp.service;

import com.BankingApp.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    public ProductDTO newProduct(String name);

    public ProductDTO getProduct(String id) throws Exception;

    public List<ProductDTO> getProducts();

}
