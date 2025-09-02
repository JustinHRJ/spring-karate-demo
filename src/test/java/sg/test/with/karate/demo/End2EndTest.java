package sg.test.with.karate.demo;

import com.intuit.karate.Runner;
import com.intuit.karate.core.MockServer;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class End2EndTest {

    private static MockServer bankServer;

    @BeforeAll
    static void startMocks() {
        bankServer = MockServer
                .feature("classpath:mocks/bank-balance-mock.feature")
                .http(0)
                .build();
        System.out.println("Bank mock at: http://localhost:" + bankServer.getPort());
    }

    @AfterAll
    static void stopMocks() {
        if (bankServer != null) bankServer.stop();
    }

    // <-- this runs before the Spring context is created
    @DynamicPropertySource
    static void registerProps(DynamicPropertyRegistry r) {
        r.add("bank.service.url", () -> "http://localhost:" + bankServer.getPort());
    }

    @LocalServerPort
    int port;

    @Test
    void runAllFeatures() {
        System.setProperty("spring.profiles.active", "test");
        System.setProperty("baseUrl", "http://localhost:" + port);

        var r = Runner.path("classpath:features").parallel(1);
        assertTrue(r.getScenariosTotal() > 0, "No Karate scenarios were discovered.");
        assertEquals(0, r.getFailCount(), r.getErrorMessages());
    }
}
