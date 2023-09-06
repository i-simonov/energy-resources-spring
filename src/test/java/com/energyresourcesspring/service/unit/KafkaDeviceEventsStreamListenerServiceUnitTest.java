package com.energyresourcesspring.service.unit;

import com.energyresourcesspring.service.DeviceStateService;
import com.energyresourcesspring.service.KafkaDeviceEventsStreamListenerService;
import com.energyresourcesspring.service.generated.CanonicalKeyService;
import com.energyresourcesspring.service.generated.CanonicalValueService;
import org.apache.avro.util.Utf8;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KafkaDeviceEventsStreamListenerServiceUnitTest {

    @Mock
    private DeviceStateService deviceStateService;

    @Mock
    private StreamsBuilder streamsBuilderMock;

    @Mock
    private KStream<CanonicalKeyService, CanonicalValueService> kStreamMock;

    @Mock
    private CanonicalKeyService canonicalKey;

    @Mock
    private CanonicalValueService canonicalValue;

    @InjectMocks
    private KafkaDeviceEventsStreamListenerService listenerService;


    @BeforeEach
    void setUp() {

        HashMap<CharSequence, CharSequence> eventsMap = new HashMap<>(1);
        eventsMap.put(new Utf8("charging"), new Utf8("1"));

        when(canonicalValue.getEvents()).thenReturn(eventsMap);
        when(canonicalKey.getUuid()).thenReturn("TestUuid");
        when(streamsBuilderMock.stream(eq("devicesCanonicalEvents"), isA(Consumed.class))).thenReturn(kStreamMock);

        doAnswer(invocation -> {
            ForeachAction<CanonicalKeyService, CanonicalValueService> action = invocation.getArgument(0);
            action.apply(canonicalKey, canonicalValue);
            return null;
        }).when(kStreamMock).foreach(any());

    }

    @Test
    public void kStreamCanonicalSaveFilteringOnChargingKeyTest() {

        HashMap<CharSequence,CharSequence> eventsMap = new HashMap<>(1);
        eventsMap.put(new Utf8("charging"), new Utf8("1"));

        CanonicalValueService valueWithCharging = new CanonicalValueService();
        valueWithCharging.setEvents(eventsMap);

        CanonicalValueService valueWithoutCharging = new CanonicalValueService();
        valueWithoutCharging.setEvents(new HashMap<>());

        //local behavior
        when(kStreamMock.filter(any())).thenAnswer(invocation -> {
            Predicate<CanonicalKeyService, CanonicalValueService> predicate = invocation.getArgument(0);
            assertTrue(predicate.test(null, valueWithCharging));
            assertFalse(predicate.test(null, valueWithoutCharging));
            return kStreamMock;
        });

        listenerService.kStreamCanonical(streamsBuilderMock);

    }

    @Test
    public void kStreamCanonicalSaveDeviceStateTest() {

        //local behavior
        when(kStreamMock.filter(any())).thenReturn(kStreamMock);

        listenerService.kStreamCanonical(streamsBuilderMock);

        verify(deviceStateService).saveDeviceState(canonicalKey.getUuid().toString(), true);

    }

    @Test
    public void kStreamJsonTest() {



    }
}
