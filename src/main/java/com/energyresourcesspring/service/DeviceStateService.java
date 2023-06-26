package com.energyresourcesspring.service;

import com.energyresourcesspring.model.DeviceState;

public interface DeviceStateService {

    DeviceState saveDeviceState(String uuid, DeviceEventAvroService deviceEvent);
    DeviceState loadDeviceState(String uuid);

}
