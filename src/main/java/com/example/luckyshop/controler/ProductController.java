package com.example.luckyshop.controler;// исправлено "controller"

import com.example.luckyshop.model.Product;
import com.example.luckyshop.service.ProductService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping // исправлено на точное значение маршрута
    public String getAllProducts(Model model) {
        // Добавляем все продукты в модель, чтобы Thymeleaf мог их использовать
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product_list";  // возвращаем имя шаблона Thymeleaf
    }

    @GetMapping("/new")
    public String createProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product_form";  // форма для создания нового продукта
    }

    @PostMapping // исправлено на точное значение маршрута
    public String addProduct(@ModelAttribute Product product) {
        productService.addProduct(product);
        return "redirect:/products";  // редирект обратно к списку продуктов
    }

    @GetMapping("/{id}") // О товаре
    public String getProductById(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            // Обработка случая, когда продукт не найден
            model.addAttribute("error", "Product not found");
            return "error_page";  // создайте шаблон error_page для отображения ошибок
        }
        model.addAttribute("product", product);
        return "product_detail";  // отображение деталей продукта
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";  // редирект обратно к списку продуктов после удаления
    }
    @GetMapping("/edit/{id}")  // Получение формы для редактирования продукта
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            model.addAttribute("error", "Product not found");
            return "error_page"; // Отправка на страницу с ошибкой, если продукт не найден
        }

        model.addAttribute("product", product); // Добавляем продукт в модель
        return "product_edit_form";  // Имя шаблона формы редактирования
    }
    @PostMapping("/edit/{id}")  // Обработка обновления данных продукта
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product updatedProduct) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/products"; // Если продукт не найден, перенаправляем обратно к списку продуктов
        }

        // Обновляем продукт данными из формы
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());

        productService.updateProduct(product); // Сохраняем изменения в базе данных
        return "redirect:/products";  // Редирект к списку продуктов после сохранения
    }
}

