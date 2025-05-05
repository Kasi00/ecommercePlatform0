package com.example.ecommercePlatform.category;

import com.example.ecommercePlatform.category.model.CategoryRequest;
import com.example.ecommercePlatform.category.model.CategoryResponse;
import com.example.ecommercePlatform.category.persistance.Category;
import com.example.ecommercePlatform.category.persistance.CategoryRepository;
import com.example.ecommercePlatform.product.ProductService;
import com.example.ecommercePlatform.product.model.ProductResponse;
import com.example.ecommercePlatform.product.persistance.Product;
import com.example.ecommercePlatform.product.persistance.ProductRepository;
import com.example.ecommercePlatform.user.Persistance.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    public List<Category> findAll() {
       return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public CategoryResponse getCategoryWithProducts(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return maptoCategoryResponse(category);
    }

    private CategoryResponse maptoCategoryResponse(Category category) {
        List<ProductResponse> productResponses=category.getProducts()
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
        CategoryResponse response= new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setProducts(productResponses);
        return response;

    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setImageUrl(product.getImageUrl());
        return response;
    }


    public Category createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        Set<Product> products= request.getProductIds().stream().map(productRepository::findById).map(
                productOpt -> productOpt.orElseThrow(()
                        -> new RuntimeException("Product not found"))).collect(Collectors.toSet());
        category.setProducts(products);
        return categoryRepository.save(category);
    }

    public Category update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(request.getName());
        Set<Product> products = request.getProductIds().stream()
                .map(productRepository::findById)
                .map(productOpt -> productOpt.orElseThrow(() -> new RuntimeException("Product not found")))
                .collect(Collectors.toSet());
        category.setProducts(products);
       return categoryRepository.save(category);
    }


    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }






}
