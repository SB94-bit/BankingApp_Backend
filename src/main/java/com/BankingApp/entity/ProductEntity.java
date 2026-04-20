package com.BankingApp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
