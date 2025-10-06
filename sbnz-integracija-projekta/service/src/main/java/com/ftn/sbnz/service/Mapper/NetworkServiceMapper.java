package com.ftn.sbnz.service.Mapper;

import com.ftn.sbnz.model.models.NetworkService;
import com.ftn.sbnz.service.DTO.NetworkServiceDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NetworkServiceMapper {
    private final ModelMapper mapper = new ModelMapper();

    public NetworkServiceDTO toDto(NetworkService entity) {
        return mapper.map(entity, NetworkServiceDTO.class);
    }

    public NetworkService toEntity(NetworkServiceDTO dto) {
        return mapper.map(dto, NetworkService.class);
    }
}
