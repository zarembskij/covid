package com.zarembski.covid.requests;

import com.zarembski.covid.model.CovidData;
import com.zarembski.covid.service.CovidDataService;
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
public class HerokuappApiRequest {

    private static final String REST_URI = "https://coronavirus-19-api.herokuapp.com/countries/Poland";
    private Client client = ClientBuilder.newClient();
    private final CovidDataService covidDataService;

    // @Scheduled(fixedRate = 300000)
    private void getData() {
        log.info(String.format("Getting data from url: %s", REST_URI));
        LmaoResullt result =  client
                .target(REST_URI)
                .request()
                .get(LmaoResullt.class);
        covidDataService.saveCovidDataWhenNewData(result.buildCovidData());
    }
}

@Data
class Resullt {
        private String country;
        private long cases;
        private long todayCases;
        private long deaths;
        private long todayDeaths;
        private long recovered;
        private long active;
        private long critical;
        private long casesPerOneMillion;

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
