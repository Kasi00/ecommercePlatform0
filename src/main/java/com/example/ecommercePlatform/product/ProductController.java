package com.example.ecommercePlatform.product;

import com.example.ecommercePlatform.product.model.ProductRequest;
import com.example.ecommercePlatform.product.persistance.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id){
      return   productService.getProduct(id);
    }

    @GetMapping("/all")
    public List<Product> getProducts(){
        return productService.getAllProducts();
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody ProductRequest request){
        productService.createProduct(request);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody ProductRequest request){
       return productService.updateProduct(id, request);
    }




    @PutMapping("/{id}/quantityChange")
    public void updateStock(@PathVariable Long id,@RequestParam Long quantityChange){
      productService.updateStock(id,quantityChange);
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }




}
