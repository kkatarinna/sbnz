package com.ftn.sbnz.service.insert;

import com.ftn.sbnz.model.models.Device;
import com.ftn.sbnz.model.models.NetworkService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InsertDevices {
    public static List<Device> generateDevices() {
        Device dev1 = new Device(UUID.randomUUID(), "123.123.123.123", "Linux_mint", 4, "Linux");
        Device dev2 = new Device(UUID.randomUUID(), "122.241.21.52", "Windows", 10, "Windows");
        Device dev3 = new Device(UUID.randomUUID(), "185.10.10.1", "Linux_ubuntu", 4, "Linux");
        Device dev4 = new Device(UUID.randomUUID(), "193.55.33.8", "macOS", 4, "macOS");
        Device dev5 = new Device(UUID.randomUUID(), "10.0.0.77", "Linux_kali", 4, "Linux");
        List<Device> devices = new ArrayList<>();
        devices.add(dev1);
        devices.add(dev2);
        devices.add(dev3);
        devices.add(dev4);
        devices.add(dev5);
        return devices;
    }
}
