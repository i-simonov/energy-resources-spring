package com.energyresourcesspring.service.impl;

import com.energyresourcesspring.service.DeviceStateService;
import com.energyresourcesspring.model.DeviceState;
import com.energyresourcesspring.repository.DeviceStateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeviceStateServiceImpl implements DeviceStateService {

    private DeviceStateRepository deviceStateRepository;

    public DeviceStateServiceImpl(DeviceStateRepository deviceStateRepository) {
        this.deviceStateRepository = deviceStateRepository;
    }


    @Override
    public DeviceState saveDeviceState(String uuid, boolean state) {

        DeviceState deviceState = new DeviceState();
        deviceState.setUuid(uuid);
        deviceState.setState(state);

        return deviceStateRepository.save(deviceState);

    }

    @Override
    public DeviceState loadDeviceState(String uuid) {
        return deviceStateRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("DeviceState with uuid " + uuid + " not found"));
    }


}
