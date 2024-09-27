package com.seuprojeto.projeto_web.services;


import com.seuprojeto.projeto_web.entities.Category;
import com.seuprojeto.projeto_web.repositories.CategoryRepository;
import com.seuprojeto.projeto_web.requests.CategoryRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    ModelMapper modelMapper = new ModelMapper();


    public CategoryRequest createCategory(CategoryRequest categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new IllegalArgumentException("A category with this name already exists.");
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryRequest.class);
    }

    public CategoryRequest updateCategory(Long id, CategoryRequest categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found."));

        modelMapper.map(categoryDTO, category);
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryRequest.class);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found."));
        categoryRepository.delete(category);
    }

    public List<CategoryRequest> listCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryRequest.class))
                .toList();
    }

    public CategoryRequest getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found."));
        return modelMapper.map(category, CategoryRequest.class);
    }
}
