package com.sdgcrm.application.repository;

import com.sdgcrm.application.data.entity.Customer;
import com.sdgcrm.application.data.entity.Employee;
import com.sdgcrm.application.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByEmployer(User loggedincompany);

    @Query("select c from Employee c where lower(c.name) like lower(concat('%', ?1, '%')) and c.employer.id = ?2 ")
    List<Employee> search(String searchTerm, long userId);

}
