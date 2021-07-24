package com.smc.stockmarketcharting.repositories;

import com.smc.stockmarketcharting.models.ERole;
import com.smc.stockmarketcharting.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>, JpaSpecificationExecutor<Role> {
    Optional<Role> findByName(ERole name);
}
