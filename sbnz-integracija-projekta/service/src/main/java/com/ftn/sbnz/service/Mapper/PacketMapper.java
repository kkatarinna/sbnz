package com.ftn.sbnz.service.Mapper;

import com.ftn.sbnz.model.events.PacketEvent;
import com.ftn.sbnz.service.DTO.PacketDTO;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Component
public class PacketMapper {
    private final ModelMapper mapper = new ModelMapper();

    public PacketDTO toDto(PacketEvent entity) {
        return mapper.map(entity, PacketDTO.class);
    }

    public PacketEvent toEntity(PacketDTO dto) {
        if (dto.getExecutionTime() == null){
            dto.setExecutionTime(new Date());
        }
        return mapper.map(dto, PacketEvent.class);
    }
}
