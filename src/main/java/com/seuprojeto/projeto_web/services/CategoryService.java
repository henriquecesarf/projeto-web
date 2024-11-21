package com.seuprojeto.projeto_web.services;

import com.seuprojeto.projeto_web.entities.CategoryEntity;
import com.seuprojeto.projeto_web.repositories.CategoryRepository;
import com.seuprojeto.projeto_web.requests.CategoryRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    // Método para criar uma categoria e limpar o cache após salvar
    @CacheEvict(value = "categories", allEntries = true)
    public CategoryRequest createCategory(CategoryRequest categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new IllegalArgumentException("The category with that name already exists.");
        }
        CategoryEntity categoryEntity = modelMapper.map(categoryDTO, CategoryEntity.class);
        categoryRepository.save(categoryEntity);
        return modelMapper.map(categoryEntity, CategoryRequest.class);
    }

    // Método para atualizar uma categoria e remover a entrada específica do cache
    @CacheEvict(value = "categories", key = "#id")
    public CategoryRequest updateCategory(Long id, CategoryRequest categoryDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found."));
        modelMapper.map(categoryDTO, categoryEntity);
        categoryRepository.save(categoryEntity);
        return modelMapper.map(categoryEntity, CategoryRequest.class);
    }

    // Método para deletar uma categoria e limpar o cache da categoria específica
    @CacheEvict(value = "categories", key = "#id")
    public void deleteCategory(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found."));
        categoryRepository.delete(categoryEntity);
    }

    // Método para listar todas as categorias, com cache
    @Cacheable(value = "categories")
    public List<CategoryRequest> listCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryRequest.class))
                .collect(Collectors.toList());
    }

    // Método para buscar uma categoria específica, com cache
    @Cacheable(value = "categories", key = "#id")
    public CategoryRequest getCategory(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found."));
        return modelMapper.map(categoryEntity, CategoryRequest.class);
    }
}
