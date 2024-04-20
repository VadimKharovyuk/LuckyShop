package com.example.luckyshop.controler;

import com.example.luckyshop.model.Cart;
import com.example.luckyshop.model.Customer;
import com.example.luckyshop.model.Product;
import com.example.luckyshop.service.CartService;
import com.example.luckyshop.service.CustomerService;
import com.example.luckyshop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public String viewCart(@PathVariable Long customerId, Model model) {
        Optional<Customer> customerOpt = customerService.getCustomerById(customerId); // Получаем Optional<Customer>

        if (customerOpt.isPresent()) { // Проверяем, существует ли клиент
            Customer customer = customerOpt.get(); // Получаем объект Customer
            Cart cart = cartService.getCartByCustomer(customer); // Получаем корзину
            model.addAttribute("cart", cart); // Добавляем корзину в модель
            return "cart_view"; // Возвращаем шаблон отображения корзины
        } else {
            model.addAttribute("error", "Customer not found");
            return "error_page"; // Если клиент не найден, возвращаем страницу ошибки
        }
    }


    @PostMapping("/{customerId}/add/{productId}")
    public String addToCart(@PathVariable Long customerId, @PathVariable Long productId) {
        Optional<Customer> customerOpt = customerService.getCustomerById(customerId); // Получаем Optional<Customer>

        if (customerOpt.isPresent()) {
            Product product = productService.getProductById(productId);
            Customer customer = customerOpt.get(); // Получаем Customer из Optional
            cartService.addProductToCart(customer, product); // Добавляем продукт в корзину для данного клиента
            return "redirect:/cart/" + customerId; // Редирект обратно к корзине
        } else {
            return "error_page"; // Если клиент не найден, возвращаем страницу с ошибкой
        }
    }


    @PostMapping("/{customerId}/remove/{productId}")
    public String removeFromCart(@PathVariable Long customerId, @PathVariable Long productId) {
        Optional<Customer> customerOpt = customerService.getCustomerById(customerId); // Получаем Optional<Customer>

        if (customerOpt.isPresent()) {
            Product product = productService.getProductById(productId);
            Customer customer = customerOpt.get(); // Извлекаем Customer из Optional
            cartService.removeProductFromCart(customer, product); // Удаляем продукт из корзины для данного клиента
            return "redirect:/cart/" + customerId; // Редирект к корзине
        } else {
            return "error_page"; // Если клиент не найден, переход на страницу с ошибкой
        }
    }

}
