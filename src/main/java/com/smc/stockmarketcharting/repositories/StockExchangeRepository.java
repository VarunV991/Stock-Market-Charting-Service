package com.smc.stockmarketcharting.repositories;

import com.smc.stockmarketcharting.models.StockExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockExchangeRepository extends JpaRepository<StockExchange,Long>,
        JpaSpecificationExecutor<StockExchange> {

    public Optional<StockExchange> findStockExchangeByName(String exchangeName);
}
