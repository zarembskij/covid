package com.zarembski.covid.service;

import com.zarembski.covid.model.CovidData;
import com.zarembski.covid.model.LcdCovidData;
import com.zarembski.covid.repository.CovidDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CovidDataService {

    private final CovidDataRepository covidDataRepository;

    @Transactional (propagation = Propagation.REQUIRES_NEW)
    public void saveCovidDataWhenNewData(CovidData covidData) {
        CovidData lastData = covidDataRepository.findFirstByOrderByDateDesc();
        if (!covidData.equals(lastData)) {
            covidDataRepository.save(covidData);
        }
    }

    public CovidData getLastData() {
        return covidDataRepository.findFirstByOrderByDateDesc();
    }

    public LcdCovidData getLcdLastDate() {
        CovidData lastData = covidDataRepository.findFirstByOrderByDateDesc();
        return LcdCovidData.builder().firstLine(getFirstLcdLine(lastData))
                .secondLine(getSecondLine(lastData)).build();
    }

    private String getFirstLcdLine(CovidData data) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Cov ").append(data.getCases()).append("/").append(data.getTodayCases()).toString();
    }

    private String getSecondLine(CovidData data) {
        StringBuilder builder = new StringBuilder();
        return builder.append("zm/wy ").append(data.getDeaths()).append("(").append(data.getTodayDeaths())
                .append(")").append("/").append(data.getRecovered()).toString();
    }
}
