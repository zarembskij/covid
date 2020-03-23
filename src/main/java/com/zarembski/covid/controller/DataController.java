package com.zarembski.covid.controller;

import com.zarembski.covid.service.CovidDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DataController {

    private final CovidDataService covidDataService;

    @RequestMapping("/covid/poland")
    public String getLastRecord() {
        return covidDataService.getLastData().toString();
    }
}
