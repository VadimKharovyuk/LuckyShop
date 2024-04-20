package com.example.luckyshop.repository;

import com.example.luckyshop.model.Cart;
import com.example.luckyshop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByCustomer(Customer customer);

}
