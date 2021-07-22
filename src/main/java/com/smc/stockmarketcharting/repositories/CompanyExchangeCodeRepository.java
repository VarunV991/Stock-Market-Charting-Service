package com.smc.stockmarketcharting.repositories;

import com.smc.stockmarketcharting.models.Company;
import com.smc.stockmarketcharting.models.CompanyExchangeCode;
import com.smc.stockmarketcharting.models.StockExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyExchangeCodeRepository extends JpaRepository<CompanyExchangeCode,Long>,
        JpaSpecificationExecutor<CompanyExchangeCode> {

    @Query("from CompanyExchangeCode c where c.stockExchange=:stockExchange and c.companyCode=:companyCode")
    public Optional<CompanyExchangeCode> findCompanyByCompanyCode(
            @Param(value = "stockExchange") StockExchange stockExchange,
            @Param(value = "companyCode") String companyCode);

    public CompanyExchangeCode findCompanyExchangeCodeByCompanyAndStockExchange(Company company,
                                                                                StockExchange stockExchange);

    public List<CompanyExchangeCode> findCompanyExchangeCodesByStockExchange(StockExchange exchange);
    public List<CompanyExchangeCode> findCompanyExchangeCodesByCompany(Company company);
}
