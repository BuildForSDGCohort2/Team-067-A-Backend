package com.sdgcrm.application.repository;

import java.util.Optional;

import com.sdgcrm.application.data.entity.Role;
import com.sdgcrm.application.data.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}