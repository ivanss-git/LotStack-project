package com.carauction.application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CarAuctionApplicationTests {

    /**
     * This test is auto created under src when you generate a project from Spring initialize.
     */
    @Test
    void contextLoads() {
        // This test will fail if the Spring ApplicationContext cannot start.
        // Requires no assertions becaude the initialization process itself is what we are testing.
    }
}