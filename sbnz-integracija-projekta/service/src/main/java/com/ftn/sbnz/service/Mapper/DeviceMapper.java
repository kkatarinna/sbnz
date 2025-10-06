package com.ftn.sbnz.service.Mapper;

import com.ftn.sbnz.model.models.Device;
import com.ftn.sbnz.service.DTO.DeviceDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {
    private final ModelMapper mapper = new ModelMapper();

    public DeviceDTO toDto(Device entity) {
        return mapper.map(entity, DeviceDTO.class);
    }

    public Device toEntity(DeviceDTO dto) {
        return mapper.map(dto, Device.class);
    }
}
