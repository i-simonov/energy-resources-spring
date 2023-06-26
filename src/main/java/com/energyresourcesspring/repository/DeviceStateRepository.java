package com.energyresourcesspring.repository;

import com.energyresourcesspring.model.DeviceState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceStateRepository extends JpaRepository<DeviceState, String> {

}
