package com.sdgcrm.application.data.service;

import com.sdgcrm.application.data.entity.Customer;
import com.sdgcrm.application.data.entity.Deal;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.repository.CustomerRepository;
import com.sdgcrm.application.repository.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Service
public class DealService  implements Serializable {

    @Autowired
    DealRepository dealRepository;


    public DealRepository getDealRepository() {
        return dealRepository;
    }

    public void setDealRepository(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }


    public void saveDeal(Deal deal) {
        dealRepository.save(deal);
    }

    public List<Deal> findAll(){
       return dealRepository.findAll();

    }

    public List<String> getDealStatus() {
        return Arrays.asList("Completed", "In progress", "Lead");
    }

    public void delete(Deal deal) {
        dealRepository.delete(deal);
    }

//    public int getTotalCustomers(User currentUser) {
//        return dealRepository.findByCompany(currentUser).size();
//    }


}
