package com.smc.stockmarketcharting.repositories;

import com.smc.stockmarketcharting.models.CompanyExchangeCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompanyExchangeCodeRepository extends JpaRepository<CompanyExchangeCode,Long>,
        JpaSpecificationExecutor<CompanyExchangeCode> {
}
