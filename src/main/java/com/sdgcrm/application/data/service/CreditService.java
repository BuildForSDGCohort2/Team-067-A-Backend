package com.sdgcrm.application.data.service;

import com.sdgcrm.application.data.entity.Credit;
import com.sdgcrm.application.data.entity.Deal;
import com.sdgcrm.application.repository.CompanyRepository;
import com.sdgcrm.application.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditService {

    @Autowired
    CreditRepository creditRepository;


    public void saveCredit(Credit credit) {
        creditRepository.save(credit);
    }




    public List<Credit> findAll(){
        return creditRepository.findAll();

    }

    public void delete(Credit credit) {
        creditRepository.delete(credit);
    }
}
