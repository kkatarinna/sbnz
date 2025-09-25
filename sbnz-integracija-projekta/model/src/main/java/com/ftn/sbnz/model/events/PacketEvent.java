package com.ftn.sbnz.model.events;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.time.DateUtils;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.util.Date;
import java.util.UUID;

@Role(Role.Type.EVENT)
@Getter
@Setter
@Timestamp("executionTime")
@Expires("10m")
@RequiredArgsConstructor
public class PacketEvent {


    private UUID id = UUID.randomUUID();

    @NonNull
    private String sourceIP;
    @NonNull
    private String destinationIP;
    @NonNull
    private int sourcePort;
    @NonNull
    private int destinationPort;
    @NonNull
    private Protocol protocol;

    private Date executionTime = new Date();

}
