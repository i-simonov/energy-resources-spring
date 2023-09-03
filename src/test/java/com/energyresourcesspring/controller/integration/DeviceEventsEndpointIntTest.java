package com.energyresourcesspring.controller.integration;

import com.energyresourcesspring.controller.DeviceEventsEndpoint;
import com.energyresourcesspring.service.generated.RawRecordAvroService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@WebMvcTest(DeviceEventsEndpoint.class)
public class DeviceEventsEndpointIntTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KafkaTemplate<String, RawRecordAvroService> kafkaTemplate;

    @Captor
    private ArgumentCaptor<RawRecordAvroService> messageCaptor;

    @Test
    public void testPublish() throws Exception {
        String deviceId = "12345";
        String messageBody = "Test message";

        mockMvc.perform(post("/api/v1/deviceEvents/" + deviceId)
                        .contentType("text/plain")
                        .content(messageBody))
                .andExpect(status().isOk())
                .andDo(print());

        verify(kafkaTemplate).send(eq("devicesEvents"), eq(deviceId), messageCaptor.capture());
        RawRecordAvroService sentMessage = messageCaptor.getValue();
        assertThat(sentMessage.getUuid()).isEqualTo(deviceId);
        assertThat(sentMessage.getBody().array()).isEqualTo(messageBody.getBytes());
    }
}
