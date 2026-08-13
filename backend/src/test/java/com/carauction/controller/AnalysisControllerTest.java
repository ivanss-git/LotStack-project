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
// Loads only the web layer for analysis controller
@WebMvcTest(AnalysisController.class)
class AnalysisControllerTest {

    //Provides the fake http client without needing db to startup
    @Autowired
    private MockMvc mockMvc;

    // Creates a fake service without running postgres or db
    @MockitoBean
    private AnalysisService service;

    // Simulates an http delete request
    @Test
    void deleteAnalysisReturnsNoContent() throws Exception {
        mockMvc
            .perform(delete("/api/vehicles/1/analysis"))
            .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }
}