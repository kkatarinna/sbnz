package com.ftn.sbnz.service.DTO;

import com.ftn.sbnz.model.enums.Flag;
import com.ftn.sbnz.model.enums.Protocol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PacketDTO {
    private String sourceIP;
    private String destinationIP;
    private int sourcePort;
    private int destinationPort;
    private Protocol protocol;
    private List<Flag> flags;
    private long payloadSize;
    private String dnsQuery;
    private Date executionTime = new Date();
}
