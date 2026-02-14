package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.entity.Category;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repo.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // Constructor Injection (Manual)
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(CategoryDto dto) {

        Optional<Category> existing = categoryRepository.findByName(dto.getName());

        if (existing.isPresent()) {
        	throw new BadRequestException("Category already exists");
        }

        Category category = new Category();
        category.setName(dto.getName());

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
