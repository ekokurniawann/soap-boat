package com.mcnz.spring.soap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mcnz.jee.soap.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}

