package com.smc.stockmarketcharting.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Sector")
public class Sector {

    @GeneratedValue
    @Id
    private long id;
    private String name;
    private String description;

    @OneToMany(targetEntity = Company.class)
    private List<Company> companies;
}
