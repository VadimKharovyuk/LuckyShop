package com.example.luckyshop.service;

import com.example.luckyshop.model.Product;
import com.example.luckyshop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void updateProduct(Product product) {
        // Убедитесь, что продукт существует
        Optional<Product> existingProduct = productRepository.findById(product.getId());

        if (existingProduct.isPresent()) {
            // Если продукт найден, сохраняем обновленный продукт
            productRepository.save(product);
        } else {
            // Если продукт не найден, выбрасываем исключение или обрабатываем ошибку
            throw new IllegalArgumentException("Product with id " + product.getId() + " does not exist");
        }
    }
}
