package com.zarembski.covid.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(exclude = {"date", "id"})
@Table(name = "CovidData")
@ToString
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
