package com.example.luckyshop.controller;

import com.example.luckyshop.model.Customer;
import com.example.luckyshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Получить всех клиентов
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Получить клиента по ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Добавить нового клиента
    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer newCustomer) {
        Customer customer = customerService.addCustomer(newCustomer);
        return ResponseEntity.ok(customer);
    }

    // Обновить клиента
    @PutMapping("/{id}")
    public ResponseEntity<Optional<Customer>> updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer updatedCustomer) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            Optional<Customer> updated = customerService.updateCustomer(id, updatedCustomer);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Удалить клиента
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
