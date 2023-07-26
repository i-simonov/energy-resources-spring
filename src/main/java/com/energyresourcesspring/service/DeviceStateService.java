package com.energyresourcesspring.service;

import com.energyresourcesspring.model.DeviceState;

public interface DeviceStateService {

    DeviceState saveDeviceState(String uuid, boolean state);
    DeviceState loadDeviceState(String uuid);

}
