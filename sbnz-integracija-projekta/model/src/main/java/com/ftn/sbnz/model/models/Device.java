package com.ftn.sbnz.model.models;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    private UUID id = UUID.randomUUID();

    @NonNull
    private String ip;

    @NonNull
    private String osName;

    @NonNull
    private String osVersion;

    @NonNull
    private String osType; // npr. "Windows", "Linux", "MacOS"

    @Override
    public String toString() {
        return String.format("Device{id=%s, ip=%s, osName=%s, osVersion=%s, osArch=%s, osType=%s}",
                id, ip, osName, osVersion, osType);
    }
}