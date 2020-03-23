package com.zarembski.covid.service;

import com.zarembski.covid.model.CovidData;
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
}
