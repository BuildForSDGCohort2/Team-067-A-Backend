package com.sdgcrm.application.repository;

import com.sdgcrm.application.data.entity.Asset;
import com.sdgcrm.application.data.entity.CompanyProfile;
import com.sdgcrm.application.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    @Query("select c from Asset c where lower(c.name) like lower(concat('%', ?1, '%')) and c.company.id = ?2 ")
    List<Asset> search(String stringFilter, Long id);

    List<Asset> findByCompany(CompanyProfile loggedinuser);
}
