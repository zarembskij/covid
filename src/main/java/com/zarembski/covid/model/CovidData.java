package com.zarembski.covid.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@EqualsAndHashCode(exclude = {"date"})
@Table(name = "CovidData")
public class CovidData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long cases;
    private Long todayCases;
    private Long deaths;
    private Long todayDeaths;
    private Long recovered;
    private LocalDateTime date;
}
