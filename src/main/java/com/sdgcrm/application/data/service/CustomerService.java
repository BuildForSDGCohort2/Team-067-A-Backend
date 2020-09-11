package com.sdgcrm.application.data.service;

import com.sdgcrm.application.data.entity.Customer;
import com.sdgcrm.application.data.entity.Person;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.repository.CustomerRepository;
import com.sdgcrm.application.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import java.io.Serializable;
import java.util.List;

@Service
public class  CustomerService implements Serializable {
    @Autowired
     CustomerRepository customerRepository;


    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public List<Customer> findAll(String stringFilter, User loggedinuser){

        if (stringFilter == null || stringFilter.isEmpty()) {
            return customerRepository.findByCompany(loggedinuser);
        } else {
            return customerRepository.search(stringFilter, loggedinuser.getId());
        }

    }

    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    public int getTotalCustomers(User currentUser) {
        return customerRepository.findByCompany(currentUser).size();
    }
}