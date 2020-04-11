package com.zarembski.covid.service;

import com.google.common.collect.Lists;
import com.zarembski.covid.model.CovidData;
import com.zarembski.covid.model.LcdCovidData;
import com.zarembski.covid.repository.CovidDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public List<CovidData> getAllHistoricalData() {
        return Lists.newArrayList(covidDataRepository.findAll());
        //TODO
    }

    private String getFirstLcdLine(CovidData data) {
        StringBuilder builder = new StringBuilder();
        return builder.append("C ").append(data.getCases()).append("/").append(data.getTodayCases()).toString();
    }

    private String getSecondLine(CovidData data) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Z/W ").append(data.getDeaths()).append("(").append(data.getTodayDeaths())
                .append(")").append("/").append(data.getRecovered()).toString();
    }
}
