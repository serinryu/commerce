package com.example.commerce.domains.item.domain;

import jakarta.persistence.Entity;

@Entity
public class Album extends ItemEntity{
    private String artist;
    private String etc;
}