package com.sdgcrm.application.data.service;

import com.sdgcrm.application.data.entity.CompanyProfile;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;


    public CompanyProfile findByUser(User user)  {
        return  companyRepository.findByUser(user);
    }

    public void store(CompanyProfile companyProfile) {
        companyRepository.save(companyProfile);
    }
}
