package com.study.ConstructionCalculatorWeb.service.ServiceImpl;

import com.study.ConstructionCalculatorWeb.entity.Customer;
import com.study.ConstructionCalculatorWeb.entity.User;
import com.study.ConstructionCalculatorWeb.repo.CustomerRepository;
import com.study.ConstructionCalculatorWeb.repo.UserRepository;
import com.study.ConstructionCalculatorWeb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Customer addCustomer(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Customer getCustomer(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Customer> getCustomers() {
        return repository.findAll();
    }

    @Override
    public User addCustomerToUser(User user, Customer customer) {
        repository.save(customer);
        return null;
    }
}
