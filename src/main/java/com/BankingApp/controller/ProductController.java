package com.BankingApp.controller;

import com.BankingApp.dto.ProductDTO;
import com.BankingApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable String id) {

        try {
            return this.productService.getProduct(id);
        } catch(Exception e) {
            return null;
        }

    }

    @GetMapping("/all")
    public List<ProductDTO> getProducts() {

        try {
            return this.productService.getProducts();
        } catch(Exception e) {
            return null;
        }

    }

    @PostMapping("/new")
    public ProductDTO newProduct(@RequestBody ProductDTO productDTO) {

        return this.productService.newProduct(productDTO.getName());

    }

}
