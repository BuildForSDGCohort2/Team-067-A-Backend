package com.sdgcrm.application.repository;

import com.sdgcrm.application.data.entity.Credit;
import com.sdgcrm.application.data.entity.CreditPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, Long> {




}
