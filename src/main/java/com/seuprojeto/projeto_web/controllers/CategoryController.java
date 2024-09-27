package com.seuprojeto.projeto_web.controllers;

import com.seuprojeto.projeto_web.requests.CategoryRequest;
import com.seuprojeto.projeto_web.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryRequest> createCategory(@RequestBody CategoryRequest categoryDTO) {
        CategoryRequest newCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(newCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryRequest> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryDTO) {
        CategoryRequest updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryRequest>> listCategories() {
        List<CategoryRequest> categories = categoryService.listCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryRequest> getCategory(@PathVariable Long id) {
        CategoryRequest category = categoryService.getCategory(id);
        return ResponseEntity.ok(category);
    }
}
