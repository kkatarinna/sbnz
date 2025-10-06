package com.ftn.sbnz.model.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class NetworkService {

    private UUID id = UUID.randomUUID();

    @NonNull
    private Device device;
    @NonNull
    private String name;
    @NonNull
    private String version;
    @NonNull
    private int port;
}
