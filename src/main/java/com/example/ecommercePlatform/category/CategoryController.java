package com.example.ecommercePlatform.category;

import com.example.ecommercePlatform.category.model.CategoryRequest;
import com.example.ecommercePlatform.category.model.CategoryResponse;
import com.example.ecommercePlatform.category.persistance.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @GetMapping("/{id}")
    public Category findCatById(@PathVariable Long id) {
         return categoryService.getCategoryById(id);

    }

    @GetMapping("/all")
    public List<Category> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("/withProduct/{categoryId}")
    public ResponseEntity<CategoryResponse> getCatWithProducts(@PathVariable Long categoryId) {
        categoryService.getCategoryWithProducts(categoryId);
        return ResponseEntity.ok(categoryService.getCategoryWithProducts(categoryId));
    }


    @PostMapping
    public Category saveIt(@RequestBody CategoryRequest request) {
       return categoryService.createCategory(request);

    }

    @PutMapping("/{id}")
    public Category updateIt(@PathVariable Long id, @RequestBody CategoryRequest request) {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteIt(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }


}
