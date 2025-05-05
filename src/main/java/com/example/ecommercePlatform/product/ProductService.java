package com.example.ecommercePlatform.product;

import com.example.ecommercePlatform.category.persistance.Category;
import com.example.ecommercePlatform.category.persistance.CategoryRepository;
import com.example.ecommercePlatform.product.model.ProductRequest;
import com.example.ecommercePlatform.product.persistance.Product;
import com.example.ecommercePlatform.product.persistance.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public Product getProduct(Long id){
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }


    public void createProduct(ProductRequest request){
        // Get the categoryId from the request
        Long categoryId = request.getCategoryId();

        // Fetch the Category by categoryId (make sure it exists)
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Create a new product
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());

        // Set the Category object (not just categoryId)
        product.setCategory(category); // This should be the Category object, not categoryId

        // Save the product to the database
        productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductRequest request) {
        // Fetch the existing product by id
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Fetch the Category by categoryId from the request
        Long categoryId = request.getCategoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Update the product details
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());

        // Set the Category object to the product
        product.setCategory(category);

        // Save the updated product
        return productRepository.save(product);
    }


    public void updateStock(Long id,Long quantityChange){
        Product product= productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        Long updatedStock= product.getStock()+quantityChange;
        if(updatedStock<0){
            throw new RuntimeException("Stock is lower than 0");
        }
        product.setStock(updatedStock);
        productRepository.save(product);

    }


    public void deleteProduct(Long id){
        Product product= productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        productRepository.delete(product);
    }











}
