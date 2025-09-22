package com.ftn.sbnz.model.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class NetworkService {

    private UUID id = UUID.randomUUID();

    @NonNull
    private Device device;
    @NonNull
    private String name;
    @NonNull
    private float version;
    @NonNull
    private int port;
}
