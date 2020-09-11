package com.sdgcrm.application.repository;

import com.sdgcrm.application.data.entity.Product;
import com.sdgcrm.application.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findByCompany(User currentUser);

    @Query("select c from Product c where lower(c.name) like lower(concat('%', ?1, '%')) and c.company.id = ?2 ")
    List<Product> search(String stringFilter, Long id);


}
