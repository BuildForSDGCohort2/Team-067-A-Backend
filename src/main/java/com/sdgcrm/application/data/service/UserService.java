package com.sdgcrm.application.data.service;


import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.repository.UserRepository;
import com.vaadin.flow.server.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements Serializable {


    @Autowired
    UserRepository userRepository;


    public void store(User userDetails)  {
        userRepository.save(userDetails);

    }


    public List<String> getCompanyPosition() {
        return Arrays.asList("Owner", "Co-Owner", "Representative");
    }


}
