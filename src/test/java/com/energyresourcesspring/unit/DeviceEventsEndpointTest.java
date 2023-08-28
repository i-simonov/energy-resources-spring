package com.energyresourcesspring.unit;

import com.energyresourcesspring.EnergyResourcesSpringApplication;
import com.energyresourcesspring.controller.DeviceStateEndpoint;
import com.energyresourcesspring.service.DeviceStateService;
import com.energyresourcesspring.service.KafkaDeviceEventsStreamListenerService;
import com.energyresourcesspring.service.generated.RawRecordAvroService;
import com.energyresourcesspring.service.impl.DeviceStateServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class DeviceEventsEndpointTest {

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
