package com.sdgcrm.application.data.service;


import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.repository.UserRepository;
import com.vaadin.flow.server.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class UserService implements Serializable {


    @Autowired
    UserRepository userRepository;


    public void store(User userDetails) throws ServiceException {
        userRepository.save(userDetails);

    }


}
