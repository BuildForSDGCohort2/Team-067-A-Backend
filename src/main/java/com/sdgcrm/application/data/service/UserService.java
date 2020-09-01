package com.sdgcrm.application.data.service;


import com.sdgcrm.application.data.entity.User;
import com.sdgcrm.application.repository.UserRepository;
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

    public User findByEmail(String email)  {


        return  userRepository.findByEmail(email);
    }

    public User findByUserToken(String token)  {


        return  userRepository.findByUserToken(token);
    }

    public List<String> getCompanyPosition() {
        return Arrays.asList("Owner");
    }


    public boolean existsByEmail(String email) {
        return  userRepository.existsByEmail(email);
    }
}
