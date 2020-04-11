package com.zarembski.covid.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zarembski.covid.model.CovidData;
import com.zarembski.covid.service.CovidDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AllDataController {

    private final CovidDataService covidDataService;

    @RequestMapping("/covid")
    public String getAllData() {
        List<CovidData> allHistoricalData = covidDataService.getAllHistoricalData();

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(allHistoricalData);
        } catch (JsonProcessingException e) {
            log.error(String.format("Cannot process object to json: ", allHistoricalData.toString()));
        }
        return "";
    }
}
