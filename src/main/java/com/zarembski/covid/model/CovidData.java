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
    private long id;
    private long cases;
    private long todayCases;
    private long deaths;
    private long todayDeaths;
    private long recovered;
    private LocalDateTime date;

    public boolean isNeedToUpdate(CovidData newData) {
        return (newData.cases > this.cases ||
                newData.todayCases > this.todayCases ||
                newData.deaths > this.deaths ||
                newData.todayDeaths > this.todayDeaths);
    }
}
