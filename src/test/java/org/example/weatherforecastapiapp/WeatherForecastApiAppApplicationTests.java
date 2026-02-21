package org.example.weatherforecastapiapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "weatherbit.api-key=dummy-key-for-tests")
class WeatherForecastApiAppApplicationTests {

    @Test
    void contextLoads () {
    }

}
