package com.study.ConstructionCalculatorWeb.service;

import com.study.ConstructionCalculatorWeb.entity.Customer;
import com.study.ConstructionCalculatorWeb.entity.User;

import java.util.List;

public interface CustomerService {
    Customer updateCustomer(Long id, Customer customer);
    Customer addCustomer(Customer customer);
    Customer getCustomer(long id);
    List<Customer> getCustomers();
    Customer addCustomerToUser( Customer customer);
    List<Customer> getCustomersByUser(long id);
}
