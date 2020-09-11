package com.sdgcrm.application.data.service;

import com.sdgcrm.application.data.entity.Customer;
import com.sdgcrm.application.data.entity.Employee;
import com.sdgcrm.application.data.entity.Product;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
@Service
public class ProductService  implements Serializable {

    @Autowired
    ProductRepository productRepository;



    public List<Product> findAll(String stringFilter, User currentUser) {

        if (stringFilter == null || stringFilter.isEmpty()) {
            return productRepository.findByCompany(currentUser);
        } else {
            return productRepository.search(stringFilter, currentUser.getId());
        }
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }


    public int getTotalProducts(User currentUser) {
        return productRepository.findByCompany(currentUser).size();
    }
}
