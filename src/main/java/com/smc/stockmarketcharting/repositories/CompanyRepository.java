package com.smc.stockmarketcharting.repositories;

import com.smc.stockmarketcharting.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long>, JpaSpecificationExecutor<Company> {

    public Optional<Company> findCompanyByName(String companyName);
    public List<Company> findCompanyByNameContaining(String pattern);
}
