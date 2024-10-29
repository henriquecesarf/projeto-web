package com.seuprojeto.projeto_web.services;


import com.seuprojeto.projeto_web.entities.CategoryEntity;
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
            throw new IllegalArgumentException("A categoryEntity with this name already exists.");
        }
        CategoryEntity categoryEntity = modelMapper.map(categoryDTO, CategoryEntity.class);
        categoryRepository.save(categoryEntity);
        return modelMapper.map(categoryEntity, CategoryRequest.class);
    }

    public CategoryRequest updateCategory(Long id, CategoryRequest categoryDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CategoryEntity not found."));

        modelMapper.map(categoryDTO, categoryEntity);
        categoryRepository.save(categoryEntity);
        return modelMapper.map(categoryEntity, CategoryRequest.class);
    }

    public void deleteCategory(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CategoryEntity not found."));
        categoryRepository.delete(categoryEntity);
    }

    public List<CategoryRequest> listCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryRequest.class))
                .toList();
    }

    public CategoryRequest getCategory(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CategoryEntity not found."));
        return modelMapper.map(categoryEntity, CategoryRequest.class);
    }
}
