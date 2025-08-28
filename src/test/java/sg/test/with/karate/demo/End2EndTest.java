package sg.test.with.karate.demo;


import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class End2EndTest {

    @LocalServerPort
    int port;

    @Test
    void runAllFeatures() {
        System.setProperty("spring.profiles.active", "test");
        System.setProperty("baseUrl", "http://localhost:" + port);

        Results r = Runner.path("classpath:features").parallel(1);

        assertTrue(r.getScenariosTotal() > 0, "No Karate scenarios were discovered. Check feature path: src/test/resources/features");
        assertEquals(0, r.getFailCount(), r.getErrorMessages());
        System.out.println("Karate report dir: " + r.getReportDir());
    }
}

