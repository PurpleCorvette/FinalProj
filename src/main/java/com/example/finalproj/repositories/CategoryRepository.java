package com.example.finalproj.repositories;

import com.example.finalproj.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    com.example.finalproj.models.Category findByName(String name);
}
