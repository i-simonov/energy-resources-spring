package com.energyresourcesspring.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "devicesState")
public class DeviceState {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uuid", nullable = false)
    private String uuid;
    @Column(name = "state")
    private boolean state;

}
