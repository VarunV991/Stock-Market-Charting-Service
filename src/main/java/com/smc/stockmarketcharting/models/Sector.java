package com.smc.stockmarketcharting.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Sector")
public class Sector {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String name;
    private String description;

    @OneToMany(targetEntity = Company.class,
            mappedBy = "sector")
    private List<Company> companies = new ArrayList<>();

    public Sector(String name,String description){
        this.name = name;
        this.description = description;
    }
}
