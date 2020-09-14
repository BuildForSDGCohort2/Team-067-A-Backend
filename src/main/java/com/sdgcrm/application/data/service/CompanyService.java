package com.sdgcrm.application.data.service;

import com.sdgcrm.application.data.entity.CompanyProfile;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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




    public List<String> getAllSectors() {
        return Arrays.asList("Agriculture", "Aviation","Commercial/ Retail",  "Construction","Education and Training", "Energy and Power Generation",
                "FMCG", "Fashion", "Financial Services", "Haulage/ Logistics","Healthcare", "ICT", "Manufacturing", "Media &amp; Entertainment",
        "Oil &amp; Gas", "Professional Services", "Telecommunication", "Tourism/ Hospitality", "Transportation", "Waste Management");
    }

}
