package com.zarembski.covid.service;

import com.google.common.collect.*;
import com.zarembski.covid.model.CovidData;
import com.zarembski.covid.model.LcdCovidData;
import com.zarembski.covid.repository.CovidDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
                .secondLine(getSecondLine(lastData))
                .thirdLine(getThirdLine(lastData))
                .fourthLine((getFourthLine((lastData)))).build();
    }

    public List<CovidData> getAllHistoricalData() {
        List<CovidData> covidData = Lists.newArrayList(covidDataRepository.findAll());
        Map<LocalDate, List<CovidData>> covidDailyMap = collectDailyMap(covidData.stream().collect(Collectors.toMap(c -> c.getDate(), c -> c)));
        return covidDailyMap.values().stream().map(l -> l.stream().max(Comparator.comparing(CovidData::getDate)).get())
                .sorted(Comparator.comparing(CovidData::getDate)).collect(Collectors.toList());
    }

    private Long getYesterdayCases() {
        List<CovidData> covidData = Lists.newArrayList(covidDataRepository.findAll());
        Map<LocalDate, List<CovidData>> covidDailyMap = collectDailyMap(covidData.stream()
                .filter(d ->d.getDate().toLocalDate().isEqual(LocalDate.now().minusDays(1L))).collect(Collectors.toMap(c -> c.getDate(), c -> c)));
        return covidDailyMap.values().stream().map(l -> l.stream().max(Comparator.comparing(CovidData::getDate)).get())
                .sorted(Comparator.comparing(CovidData::getDate)).map(c -> c.getTodayCases()).findFirst().orElse(0L);
    }

    private Map<LocalDate, List<CovidData>> collectDailyMap(Map<LocalDateTime, CovidData> covidDataMap) {
        Map<LocalDate, List<CovidData>> covidDailyMap = Maps.newHashMap();

        covidDataMap.forEach((k, v) -> {
            if (covidDailyMap.containsKey(k.toLocalDate())) {
                covidDailyMap.get(k.toLocalDate()).add(v);
            } else {
                covidDailyMap.put(k.toLocalDate(), Lists.newArrayList(v));
            }
        });

        return covidDailyMap;
    }

    private String getFirstLcdLine(CovidData data) {
        StringBuilder builder = new StringBuilder();
        return builder.append("C:").append(data.getCases()).append("/").append(data.getTodayCases()).toString();
    }

    private String getSecondLine(CovidData data) {
        StringBuilder builder = new StringBuilder();
        return builder.append("D:").append(data.getDeaths()).append("/").append(data.getTodayDeaths())
                .toString();
    }

    private String getThirdLine(CovidData data) {
        StringBuilder builder = new StringBuilder();
        return builder.append("R:").append(data.getRecovered())
                .toString();
    }

    private String getFourthLine(CovidData covidData) {
        return getYesterdayCases().toString();
    }
}
