package com.smc.stockmarketcharting.initializers;

import com.smc.stockmarketcharting.models.Sector;
import com.smc.stockmarketcharting.models.StockExchange;
import com.smc.stockmarketcharting.repositories.SectorRepository;
import com.smc.stockmarketcharting.repositories.StockExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EntityInitializer implements CommandLineRunner {
    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private StockExchangeRepository stockExchangeRepository;

    @Override
    public void run(String... args) throws Exception
    {
        /* Sector Initializer */

        sectorRepository.deleteAll();
        Sector sector1 = new Sector("Finance",
                "Companies that assist in Finance and Accounting");
        sectorRepository.save(sector1);
        Sector sector2 = new Sector("Healthcare",
                "Companies that provide Healthcare Services");
        sectorRepository.save(sector2);
        Sector sector3 = new Sector("Energy",
                "Companies that do business in the oil and natural gas industry");
        sectorRepository.save(sector3);
        Sector sector4 = new Sector("Materials",
                "Companies that provide various goods for use in manufacturing and other applications");
        sectorRepository.save(sector4);
        Sector sector5 = new Sector("IT",
                "Companies involved in the different categories of technological innovation");
        sectorRepository.save(sector5);

        /* StockExchange Initializer */

        stockExchangeRepository.deleteAll();
        StockExchange bse =new StockExchange("BSE", "Bombay Stock Exchange", "Dalal Street, Mumbai, India", "World's 10th largest stock-exchange");
        stockExchangeRepository.save(bse);
        StockExchange nse =new StockExchange("NSE", "National Stock Exchange", "Mumbai, India", "India's 4th largest stock-exchange");
        stockExchangeRepository.save(nse);
    }
}
