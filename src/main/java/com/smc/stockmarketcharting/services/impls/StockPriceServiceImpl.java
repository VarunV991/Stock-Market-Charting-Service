package com.smc.stockmarketcharting.services.impls;

import com.smc.stockmarketcharting.dtos.StockPriceDto;
import com.smc.stockmarketcharting.dtos.StockPriceOutputDto;
import com.smc.stockmarketcharting.mappers.StockPriceMapper;
import com.smc.stockmarketcharting.models.*;
import com.smc.stockmarketcharting.repositories.*;
import com.smc.stockmarketcharting.services.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StockPriceServiceImpl implements StockPriceService {

    @Autowired
    StockPriceRepository stockPriceRepository;

    @Autowired
    StockExchangeRepository stockExchangeRepository;

    @Autowired
    CompanyExchangeCodeRepository companyExchangeCodeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    SectorRepository sectorRepository;

    @Autowired
    StockPriceMapper stockPriceMapper;

    @Override
    public List<StockPriceDto> findAll() {
        return new ArrayList<>(stockPriceMapper.toStockPriceDtos(stockPriceRepository.findAll()));
    }

    @Override
    public String deleteById(long id) {
        try{
            stockPriceRepository.deleteById(id);
            return "Successfully deleted the stock price";
        }
        catch (Exception e){
            throw new RuntimeException("Could not find stock price: "+e.getMessage());
        }
    }

    @Override
    public StockPriceDto save(StockPriceDto stockPriceDto) {
        StockPrice stockPrice = stockPriceMapper.toStockPrice(stockPriceDto);
        StockExchange stockExchange = stockExchangeRepository.findStockExchangeByName(
                stockPriceDto.getStockExchangeName()).orElse(null);
        if(stockExchange != null) {
            CompanyExchangeCode companyExchangeCode =
                    companyExchangeCodeRepository.findCompanyByCompanyCode(stockExchange, stockPriceDto.getCompanyCode())
                            .orElse(null);
            if (companyExchangeCode != null) {
                Company company = companyExchangeCode.getCompany();
                stockPrice.setCompany(company);
                company.getStockPrices().add(stockPrice);
                stockPrice = stockPriceRepository.save(stockPrice);
                return stockPriceMapper.toStockPriceDto(stockPrice);
            }
        }
        return null;
    }

    @Override
    public List<StockPriceDto> saveList(List<StockPriceDto> stockPriceDtos){
        List<StockPriceDto> stockPriceDtoList = new ArrayList<>();
        for(StockPriceDto stockPriceDto:stockPriceDtos){
            StockPriceDto stockPrice = save(stockPriceDto);
            if(stockPrice!=null){
                stockPriceDtoList.add(stockPrice);
            }
        }
        return stockPriceDtoList;
    }

    @Override
    public List<StockPriceDto> getStockPricesForCompany(String name){
        Company company = companyRepository.findCompanyByName(name).orElse(null);
        if(company!=null){
            return stockPriceMapper.toStockPriceDtos(company.getStockPrices());
        }
        return null;
    }

    @Override
    public Map<String,Double> getStockPricesForCompanyComparison(
            long id,String exchangeName,String fromDate,String toDate,String periodicity){
        Company company = companyRepository.findById(id).orElse(null);
        if(company!=null){
            List<StockPriceDto> stockPriceDtos = new ArrayList<>();
            getStockPriceDtoInDateRange(fromDate, toDate, periodicity, company, stockPriceDtos,exchangeName);
            return getStockPriceOutputDtos(stockPriceDtos);
        }
        return null;
    }


    @Override
    public Map<String,Double> getStockPricesForSectorComparison(
            long id,String exchangeName,String fromDate,String toDate,String periodicity){
        Sector sector = sectorRepository.findById(id).orElse(null);
        if(sector != null){
            List<StockPriceDto> stockPriceDtos = new ArrayList<>();
            for(Company company: sector.getCompanies()){
                getStockPriceDtoInDateRange(fromDate, toDate, periodicity, company, stockPriceDtos,exchangeName);
            }
            return getStockPriceOutputDtos(stockPriceDtos);
        }
        return null;
    }

    private void getStockPriceDtoInDateRange(String fromDate, String toDate, String periodicity,
                         Company company, List<StockPriceDto> stockPriceDtos,String exchangeName) {

        List<StockPrice> stockPrices = company.getStockPrices();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Boolean> dateExistsMap = this.getCalendarMap(fromDate, toDate, periodicity);
        for(StockPrice stockPrice:stockPrices){
            String date = dateFormat.format(stockPrice.getDate());
            if(dateExistsMap.get(date)!=null && stockPrice.getStockExchangeName().equals(exchangeName)){
                stockPriceDtos.add(stockPriceMapper.toStockPriceDto(stockPrice));
            }
        }
    }

    private Map<String, Boolean> getCalendarMap(String fromPeriod,String toPeriod,String periodicity){
        Map<String, Boolean> dateExistsMap = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try{
            Calendar calendar = Calendar.getInstance();
            Date toDate = new Date(dateFormat.parse(toPeriod).getTime()+ 24 * 60 * 60 * 1000);
            while (dateFormat.parse(fromPeriod).getTime() < toDate.getTime()){
                dateExistsMap.put(fromPeriod,Boolean.TRUE);
                Date startDate = dateFormat.parse(fromPeriod);
                switch (periodicity) {
                    case "Day": {
                        Date nextDate = new Date(startDate.getTime() + 24 * 60 * 60 * 1000);
                        fromPeriod = dateFormat.format(nextDate);
                        break;
                    }
                    case "Week": {
                        Date nextDate = new Date(startDate.getTime() + 7 * 24 * 60 * 60 * 1000);
                        fromPeriod = dateFormat.format(nextDate);
                        break;
                    }
                    case "Month": {
                        calendar.setTime(startDate);
                        calendar.add(Calendar.MONTH, 1);
                        fromPeriod = dateFormat.format(calendar.getTime());
                        break;
                    }
                    case "Quarter": {
                        calendar.setTime(startDate);
                        calendar.add(Calendar.MONTH, 3);
                        fromPeriod = dateFormat.format(calendar.getTime());
                        break;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateExistsMap;
    }

    private Map<String,Double> getStockPriceOutputDtos(List<StockPriceDto> stockPriceDtos) {
        TreeMap<String, StockPriceOutputDto> datePriceMap = new TreeMap<>();
        for(StockPriceDto stockPriceDto: stockPriceDtos){
            String date = stockPriceDto.getDate();
            if(datePriceMap.get(date)==null){
                datePriceMap.put(date,new StockPriceOutputDto(date,stockPriceDto.getPrice(),1));
            }
            else{
                StockPriceOutputDto outputDto = datePriceMap.get(date);
                outputDto.setPrice(outputDto.getPrice()+stockPriceDto.getPrice());
                outputDto.setNoOfEntries(outputDto.getNoOfEntries()+1);
                datePriceMap.put(date,outputDto);
            }
        }
        List<StockPriceOutputDto> outputDtos = new ArrayList<>(datePriceMap.values());
        Map<String,Double> outputMap = new TreeMap<>();
        for(StockPriceOutputDto outputDto:outputDtos){
            outputMap.put(outputDto.getDate(),
                    Double.valueOf(outputDto.getPrice()/outputDto.getNoOfEntries()));
        }
        return outputMap;
    }
}
