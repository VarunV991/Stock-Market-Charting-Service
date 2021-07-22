package com.smc.stockmarketcharting.repositories;

import com.smc.stockmarketcharting.models.Company;
import com.smc.stockmarketcharting.models.Ipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IpoRepository extends JpaRepository<Ipo,Long>, JpaSpecificationExecutor<Ipo> {

    public Optional<Ipo> getIpoByCompany(Company company);
}
