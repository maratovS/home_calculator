package com.study.ConstructionCalculatorWeb.service;

import com.study.ConstructionCalculatorWeb.entity.Customer;
import com.study.ConstructionCalculatorWeb.entity.User;

import java.util.List;

public interface CustomerService {
    Customer addCustomer(Customer customer);
    Customer getCustomer(long id);
    List<Customer> getCustomers();
    User addCustomerToUser(User user, Customer customer);
}
