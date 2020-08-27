package com.sdgcrm.application.repository;


import com.sdgcrm.application.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

   User findByEmail(String email);




}
