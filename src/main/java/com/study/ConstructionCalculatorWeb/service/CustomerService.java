package com.study.ConstructionCalculatorWeb.service;

import com.study.ConstructionCalculatorWeb.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer addCustomer(Customer customer);

    Customer getCustomer(long id);

    List<Customer> getCustomers();

    Customer addCustomerToUser(Customer customer);

    Customer updateCustomer(Long id, Customer newCustomer);

    List<Customer> getCustomersByUser(long id);
}
