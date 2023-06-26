package com.energyresourcesspring.controller;

import com.energyresourcesspring.model.DeviceState;
import com.energyresourcesspring.service.DeviceStateService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/deviceState/{device_id}")
public class DeviceStateEndpoint {

    private DeviceStateService deviceStateService;

    public DeviceStateEndpoint(DeviceStateService deviceStateService) {
        this.deviceStateService = deviceStateService;
    }

    @GetMapping
    public DeviceState getState(@PathVariable("device_id") String device_id) throws IOException {

        return deviceStateService.loadDeviceState(device_id);

    }

}
