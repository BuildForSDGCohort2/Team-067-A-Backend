package com.sdgcrm.application.repository;

import com.sdgcrm.application.data.entity.Customer;
import com.sdgcrm.application.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {



    @Query("select c from Customer c where lower(c.fullName) like lower(concat('%', ?1, '%')) and c.company.id = ?2 ")
    List<Customer> search(String searchTerm, long userId);

    List<Customer> findByCompany(User loggedincompany);

}
