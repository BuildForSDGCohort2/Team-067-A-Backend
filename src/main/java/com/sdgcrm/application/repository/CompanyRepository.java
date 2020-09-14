package com.sdgcrm.application.repository;

import com.sdgcrm.application.data.entity.CompanyProfile;
import com.sdgcrm.application.data.entity.Customer;
import com.sdgcrm.application.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyProfile, Long> {

    CompanyProfile findByUser(User user);
}
