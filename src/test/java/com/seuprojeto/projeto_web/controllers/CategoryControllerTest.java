package com.seuprojeto.projeto_web.controllers;

import com.seuprojeto.projeto_web.requests.CategoryRequest;
import com.seuprojeto.projeto_web.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void createCategory_Success() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest("Category1", 10.0, 20.0, 30.0);

        when(categoryService.createCategory(any(CategoryRequest.class))).thenReturn(categoryRequest);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Category1\",\"fine1To4Days\":10.0,\"fine5To9Days\":20.0,\"fine10DaysOrMore\":30.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Category1"));
    }

    @Test
    void updateCategory_Success() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest("UpdatedCategory", 10.0, 20.0, 30.0);

        when(categoryService.updateCategory(anyLong(), any(CategoryRequest.class))).thenReturn(categoryRequest);

        mockMvc.perform(put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"UpdatedCategory\",\"fine1To4Days\":10.0,\"fine5To9Days\":20.0,\"fine10DaysOrMore\":30.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedCategory"));
    }

    @Test
    void deleteCategory_Success() throws Exception {
        doNothing().when(categoryService).deleteCategory(anyLong());

        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void listCategories_Success() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest("Category1", 10.0, 20.0, 30.0);

        when(categoryService.listCategories()).thenReturn(List.of(categoryRequest));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Category1"));
    }

    @Test
    void getCategory_Success() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest("Category1", 10.0, 20.0, 30.0);

        when(categoryService.getCategory(anyLong())).thenReturn(categoryRequest);

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Category1"));
    }

    @Test
    void createCategory_InvalidData_ReturnsBadRequest() throws Exception {
        // Enviando dados inválidos - nome vazio
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"fine1To4Days\":10.0,\"fine5To9Days\":20.0,\"fine10DaysOrMore\":30.0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCategory_CategoryNotFound_ReturnsNotFound() throws Exception {
        //  a categoria não foi encontrada
        when(categoryService.updateCategory(anyLong(), any(CategoryRequest.class)))
                .thenThrow(new RuntimeException("Categoria não encontrada"));

        mockMvc.perform(put("/categories/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"UpdatedCategory\",\"fine1To4Days\":10.0,\"fine5To9Days\":20.0,\"fine10DaysOrMore\":30.0}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Categoria não encontrada"));
    }

    @Test
    void deleteCategory_CategoryNotFound_ReturnsNotFound() throws Exception {
        // exclusão de uma categoria inexistente
        doThrow(new RuntimeException("Categoria não encontrada")).when(categoryService).deleteCategory(anyLong());

        mockMvc.perform(delete("/categories/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Categoria não encontrada"));
    }

    @Test
    void listCategories_EmptyList_ReturnsOk() throws Exception {
        // Testa o retorno de uma lista vazia de categorias
        when(categoryService.listCategories()).thenReturn(List.of());

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getCategory_CategoryNotFound_ReturnsNotFound() throws Exception {
        // Simula uma tentativa de obter uma categoria inexistente
        when(categoryService.getCategory(anyLong())).thenThrow(new RuntimeException("Categoria não encontrada"));

        mockMvc.perform(get("/categories/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Categoria não encontrada"));
    }

}

