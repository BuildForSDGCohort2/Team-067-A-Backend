package com.sdgcrm.application.data.service;

import com.sdgcrm.application.data.entity.Asset;
import com.sdgcrm.application.data.entity.Customer;
import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AssetService {

    @Autowired
    AssetRepository assetRepository;


    public List<Asset> findAll(String stringFilter, User loggedinuser) {


        if (stringFilter == null || stringFilter.isEmpty()) {
            return assetRepository.findByCompany(loggedinuser.getCompanyProfile());
        } else {
            return assetRepository.search(stringFilter, loggedinuser.getCompanyProfile().getId());
        }

    }


    public void saveAsset(Asset asset) {
        assetRepository.save(asset);
    }

    public void delete(Asset asset) {
        assetRepository.delete(asset);
    }

    public List<String> getAssetCategories() {
        return Arrays.asList("Software Asset", "Hardware Asset");
    }

    public List<String> getAssetHealthCategories() {
        return Arrays.asList("No Status", " Normal", "Warning", "Alert");
    }


    public List<String> getMaintenanceSchedule() {
        return Arrays.asList("Daily", "Weekly", "Monthly", "Quarterly", "Yearly");
    }

}
