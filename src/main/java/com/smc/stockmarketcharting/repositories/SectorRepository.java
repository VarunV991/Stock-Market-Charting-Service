package com.smc.stockmarketcharting.repositories;

import com.smc.stockmarketcharting.models.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector,Long>, JpaSpecificationExecutor<Sector> {

    public Optional<Sector> findSectorByName(@Param(value = "sectorName") String sectorName);
}
