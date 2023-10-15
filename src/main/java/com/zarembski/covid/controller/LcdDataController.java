package com.zarembski.covid.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zarembski.covid.model.LcdCovidData;
import com.zarembski.covid.requests.LmaoRequest;
import com.zarembski.covid.service.CovidDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LcdDataController {

    private final CovidDataService covidDataService;
    private final LmaoRequest lmaoRequest;

    @RequestMapping("/covid/poland/lcd")
    public String getLastRecord() {
        LcdCovidData lastData = covidDataService.getLcdLastDate();

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(lastData);
        } catch (JsonProcessingException e) {
            log.error(String.format("Cannot process object to json: ", lastData.toString()));
        }
        return "";
    }

    @RequestMapping("/covid/poland/data")
    public String getData() {
        return lmaoRequest.getData();
    }
}
