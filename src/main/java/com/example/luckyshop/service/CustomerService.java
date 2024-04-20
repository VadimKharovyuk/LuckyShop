package com.example.luckyshop.service;

import com.example.luckyshop.model.Customer;
import com.example.luckyshop.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    // Получить клиента по ID
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id); // Возвращает Optional<Customer>
    }

    // Получить всех клиентов
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll(); // Возвращает список всех клиентов
    }

    // Добавить нового клиента
    public Customer addCustomer(Customer customer) {
        if (customer == null || customer.getEmail() == null) {
            throw new IllegalArgumentException("Invalid customer data"); // Обработка ошибки при добавлении клиента
        }
        return customerRepository.save(customer); // Сохраняет нового клиента
    }

    // Удалить клиента по ID
    public void deleteCustomer(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid customer ID"); // Проверка перед удалением
        }
        customerRepository.deleteById(id); // Удаляет клиента
    }

    // Обновить данные клиента
    public Optional<Customer> updateCustomer(Long id, Customer updatedCustomer) {
        if (id == null || updatedCustomer == null) {
            throw new IllegalArgumentException("Invalid input"); // Проверка входных данных
        }

        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            customer.setFirstName(updatedCustomer.getFirstName());
            customer.setLastName(updatedCustomer.getLastName());
            customer.setEmail(updatedCustomer.getEmail());
            customerRepository.save(customer); // Сохраняет обновленного клиента
            return Optional.of(customer);
        }
        return Optional.empty(); // Если клиента с таким ID нет
    }
}
