package com.ftn.sbnz.service.DTO;

import com.ftn.sbnz.model.models.Device;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NetworkServiceDTO {
    private Device device;
    private String name;
    private String version;
    private int port;
}
