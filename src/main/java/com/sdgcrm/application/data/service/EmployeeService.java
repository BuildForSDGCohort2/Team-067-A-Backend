package com.sdgcrm.application.data.service;

import com.sdgcrm.application.data.entity.Employee;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class EmployeeService  implements Serializable {
    @Autowired
    EmployeeRepository employeeRepository;

    public void saveCustomer(Employee employee) {
        employeeRepository.save(employee);
    }



    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }

    public List<Employee> findAll(String stringFilter, User currentUser) {

        if (stringFilter == null || stringFilter.isEmpty()) {
            return employeeRepository.findByEmployer(currentUser);
        } else {
            return employeeRepository.search(stringFilter, currentUser.getId());
        }
    }

    public int getTotalEmployees(User currentUser) {
       return  employeeRepository.findByEmployer(currentUser).size();
    }
}
