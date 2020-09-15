package com.sdgcrm.application.repository;

import com.sdgcrm.application.data.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
}
