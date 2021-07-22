package com.smc.stockmarketcharting.repositories;

import com.smc.stockmarketcharting.models.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPriceRepository extends JpaRepository<StockPrice,Long>, JpaSpecificationExecutor<StockPrice> {
}
