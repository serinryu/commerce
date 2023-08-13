package com.example.commerce.domains.item.domain;

import jakarta.persistence.Entity;

@Entity
public class Movie extends ItemEntity{
    private String director;
    private String actor;
}