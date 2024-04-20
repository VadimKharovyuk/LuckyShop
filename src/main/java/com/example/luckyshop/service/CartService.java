package com.example.luckyshop.service;

import com.example.luckyshop.model.Cart;
import com.example.luckyshop.model.Customer;
import com.example.luckyshop.model.Product;
import com.example.luckyshop.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    public Cart getCartByCustomer(Customer customer) {
        return cartRepository.findByCustomer(customer);
    }

    public void addProductToCart(Customer customer, Product product) {
        Cart cart = getCartByCustomer(customer);
        cart.getProducts().add(product);
        cartRepository.save(cart);
    }

    public void removeProductFromCart(Customer customer, Product product) {
        Cart cart = getCartByCustomer(customer);
        cart.getProducts().remove(product);
        cartRepository.save(cart);
    }

    public void clearCart(Customer customer) {
        Cart cart = getCartByCustomer(customer);
        cart.getProducts().clear();
        cartRepository.save(cart);
    }
}
