package com.carauction.controller;

import com.carauction.service.AnalysisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(HistoryController.class)
class HistoryControllerTest {

    // Prepare, Act, Perform.
    // Tells spring to provide an object it created; Fake http client
    @Autowired
    private MockMvc mockMvc;

    // Creating the fake Service
    @MockitoBean
    private AnalysisService service;

    // Then to simulate the entire request and verify completion
    @Test 
    void deleteAnalysisReturnsNoContent() throws Exception {
        mockMvc
        .perform(delete("/api/vehicles/1/history"))
        .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }
}
