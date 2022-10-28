package com.study.ConstructionCalculatorWeb.service.ServiceImpl;

import com.study.ConstructionCalculatorWeb.entity.Customer;
import com.study.ConstructionCalculatorWeb.repo.CustomerRepository;
import com.study.ConstructionCalculatorWeb.repo.UserRepository;
import com.study.ConstructionCalculatorWeb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer addCustomerToUser(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer newCustomer) {
        Optional<Customer> oldCustomerOptional = customerRepository.findById(id);
        if (oldCustomerOptional.isPresent()){
            Customer oldCustomer = oldCustomerOptional.get();
            oldCustomer.setSurname(newCustomer.getSurname());
            oldCustomer.setName(newCustomer.getName());
            oldCustomer.setPatronymic(newCustomer.getPatronymic());
            oldCustomer.setTelephoneNumber(newCustomer.getTelephoneNumber());
            oldCustomer.setEmail(newCustomer.getEmail());
            oldCustomer.setAddress(newCustomer.getAddress());
            return customerRepository.save(oldCustomer);
        }
        return null;
    }

    @Override
    public List<Customer> getCustomersByUser(long id) {
        return customerRepository.findByUser_Id(id);
    }
}
