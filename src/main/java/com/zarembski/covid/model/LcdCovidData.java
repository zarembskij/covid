package com.zarembski.covid.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LcdCovidData {
    private String firstLine;
    private String secondLine;
}
