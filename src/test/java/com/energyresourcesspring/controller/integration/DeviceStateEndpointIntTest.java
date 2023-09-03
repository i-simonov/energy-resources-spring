package com.energyresourcesspring.controller.integration;

import com.energyresourcesspring.controller.DeviceStateEndpoint;
import com.energyresourcesspring.service.DeviceStateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(DeviceStateEndpoint.class)
public class DeviceStateEndpointIntTest {

    @MockBean
    DeviceStateService deviceStateService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getStateTest() throws Exception {

        String device_id = "123456";

        mockMvc.perform(get("/api/v1/deviceState/" + device_id))
                .andExpect(status().isOk())
                .andDo(print());

        verify(deviceStateService).loadDeviceState(device_id);
    }

}
