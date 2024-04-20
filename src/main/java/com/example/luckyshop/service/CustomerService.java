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

    public Optional<Customer> updateCustomer(Long id, Customer updatedCustomer) {
        if (id == null) {
            throw new IllegalArgumentException("ID не может быть null"); // Проверка входных данных
        }

        if (updatedCustomer == null) {
            throw new IllegalArgumentException("Обновленные данные клиента не могут быть null"); // Проверка входных данных
        }

        Optional<Customer> existingCustomerOpt = customerRepository.findById(id);
        if (existingCustomerOpt.isPresent()) {
            Customer existingCustomer = existingCustomerOpt.get();

            // Обновление данных только при наличии изменений
            if (updatedCustomer.getFirstName() != null &&
                    !updatedCustomer.getFirstName().equals(existingCustomer.getFirstName())) {
                existingCustomer.setFirstName(updatedCustomer.getFirstName());
            }

            if (updatedCustomer.getLastName() != null &&
                    !updatedCustomer.getLastName().equals(existingCustomer.getLastName())) {
                existingCustomer.setLastName(updatedCustomer.getLastName());
            }

            if (updatedCustomer.getEmail() != null &&
                    !updatedCustomer.getEmail().equals(existingCustomer.getEmail())) {
                existingCustomer.setEmail(updatedCustomer.getEmail());
            }

            // Сохранение обновленных данных
            customerRepository.save(existingCustomer);

            return Optional.of(existingCustomer); // Возвращаем обновленного клиента
        }

        return Optional.empty(); // Если клиента с таким ID нет
    }

    // Обновление данных клиента
  


}
