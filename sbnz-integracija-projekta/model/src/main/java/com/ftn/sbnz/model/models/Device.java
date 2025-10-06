package com.ftn.sbnz.model.models;
import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Device {


    private UUID id = UUID.randomUUID();

    @NonNull
    private String ip;

    @NonNull
    private String os;

}
