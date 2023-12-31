package com.zarembski.covid.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zarembski.covid.model.CovidData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class LmaoRequest {

    private static final String REST_URI = "https://corona.lmao.ninja/v2/countries/poland";
    private Client client = ClientBuilder.newClient();

    public String getData() {
        log.info(String.format("Getting data from url: %s", REST_URI));
        LmaoResultData result =  client
                .target(REST_URI)
                .request()
                .get(LmaoResultData.class);
        return result.toString();
    }
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class LmaoResultData {
    private String country;
    private long cases;
    private long todayCases;
    private long deaths;
    private long todayDeaths;
    private long recovered;
    private long active;
    private long critical;
    private long casesPerOneMillion;
    private long deathsPerOneMillion;

    public CovidData buildCovidData() {
        return CovidData.builder()
                .cases(this.cases)
                .todayCases(this.todayCases)
                .deaths(this.deaths)
                .todayDeaths(this.todayDeaths)
                .recovered(this.recovered)
                .date(LocalDateTime.now())
                .build();
    }
}
