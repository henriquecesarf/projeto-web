package com.seuprojeto.projeto_web.controllers;

import com.seuprojeto.projeto_web.requests.CategoryRequest;
import com.seuprojeto.projeto_web.services.CategoryService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;


@RestController
@RequestMapping("api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(
            summary = "Criação categoria",
            description = "Endpoint de criação de categoria"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Retorna um Json com o objeto da categoria criada"),
    })
    @PostMapping
    public ResponseEntity<CategoryRequest> createCategory(@RequestBody CategoryRequest categoryDTO) {
        CategoryRequest newCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(newCategory);
    }

    @Operation(
            summary = "Alteração de categorias",
            description = "Endpoint para fazer a alteração de uma Categoria pelo ID.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna um Json com os dados da categoria Alterada"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryRequest> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryDTO) {
        CategoryRequest updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    @Operation(
            summary = "Delete de categoria",
            description = "Endpoint para fazer Exclusão de uma categoria.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = ""),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get todas as Categoria",
            description = "Endpoint para consultar todas as categorias disponíveis.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna um Json com todas as categorias"),
    })
    @GetMapping
    public ResponseEntity<List<CategoryRequest>> listCategories() {
        List<CategoryRequest> categories = categoryService.listCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(
            summary = "Get por ID de categoria",
            description = "Endpoint para obter uma categoria em específica.\n\n"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200" , description = "Retorna um Json com os dados da categoria consultada"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryRequest> getCategory(@PathVariable Long id) {
        CategoryRequest category = categoryService.getCategory(id);
        return ResponseEntity.ok(category);
    }

}
