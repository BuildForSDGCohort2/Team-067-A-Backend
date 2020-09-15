package com.sdgcrm.application.data.service;

import com.sdgcrm.application.data.entity.Asset;
import com.sdgcrm.application.data.entity.Customer;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    @Autowired
    AssetRepository assetRepository;


    public List<Asset> findAll() {

        return assetRepository.findAll();
//        if (stringFilter == null || stringFilter.isEmpty()) {
//            return employeeRepository.findByEmployer(currentUser);
//        } else {
//            return employeeRepository.search(stringFilter, currentUser.getId());
//        }
    }


    public void saveAsset(Asset asset) {
        assetRepository.save(asset);
    }

    public void delete(Asset asset) {
        assetRepository.delete(asset);
    }

}
