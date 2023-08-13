package com.example.commerce.domains.item.domain;

import jakarta.persistence.Entity;

@Entity
public class Book extends ItemEntity {
    private String author;
    private String isbn;
}