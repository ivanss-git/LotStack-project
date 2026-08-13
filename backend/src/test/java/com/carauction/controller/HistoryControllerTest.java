package com.carauction.controller;

import com.carauction.dto.request.HistoryRequest;
import com.carauction.dto.response.HistoryResponse;
import com.carauction.service.HistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest; 
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HistoryController.class)
class HistoryControllerTest {

    // Prepare, Act, Perform.
    // Tells spring to provide an object it created; Fake http client
    @Autowired
    private MockMvc mockMvc;

    // Creating the fake Service
    @MockitoBean
    private HistoryService service;

    @Test
    void save_ValidRequest_ReturnsBadRequest() throws Exception {
        Long vehicleId = 1L;
        String invalidJson = "{}";

        mockMvc.perform(put("/api/vehicles/{vehicleId}/history", vehicleId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidJson))
            .andExpect(status().isBadRequest());

        verify(service, never()).save(eq(vehicleId), any(HistoryRequest.class));
    }

    @Test
    void get_ExistingVehicleId_ReturnsOk() throws Exception {
        Long vehicleId = 1L;

        HistoryResponse response = createResponse();
        
        when(service.get(vehicleId)).thenReturn(response);

        mockMvc.perform(get("/api/vehicles/{vehicleId}/history", vehicleId))
        .andExpect(status().isOk());

        verify(service).get(vehicleId);
    }

    // Then to simulate the entire request and verify completion
    @Test 
    void delete_ExistingAVehicleId_ReturnsNoContent() throws Exception {

        Long vehicleId = 1L;

        mockMvc
        .perform(delete(
            "/api/vehicles/{vehicleId}/history",
            vehicleId
        ))
        .andExpect(status()
        .isNoContent());

        verify(service).delete(vehicleId);
    }

    private HistoryResponse createResponse() { return null;}
}
