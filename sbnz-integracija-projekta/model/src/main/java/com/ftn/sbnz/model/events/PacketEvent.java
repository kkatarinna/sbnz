package com.ftn.sbnz.model.events;

import com.ftn.sbnz.model.enums.Flag;
import com.ftn.sbnz.model.enums.Protocol;
import lombok.*;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Role(Role.Type.EVENT)
@Getter
@Setter
@Timestamp("executionTime")
@Expires("10m")
@NoArgsConstructor
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
    @NonNull
    private List<Flag> flags;
    @NonNull
    private Long payloadSize;

    private String dnsQuery;

    private Date executionTime = new Date();

}
