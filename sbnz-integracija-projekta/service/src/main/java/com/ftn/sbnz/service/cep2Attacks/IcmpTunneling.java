package com.ftn.sbnz.service.cep2Attacks;

import com.ftn.sbnz.model.enums.Flag;
import com.ftn.sbnz.model.enums.Protocol;
import com.ftn.sbnz.model.events.PacketEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IcmpTunneling {

    public static ArrayList<PacketEvent> generateSuspiciousPackets() {
        PacketEvent packet1 = new PacketEvent();
        packet1.setSourceIP("172.16.1.0");
        packet1.setSourcePort(6052);
        packet1.setPayloadSize(400L);
        packet1.setDestinationIP("192.168.1.101");
        packet1.setDestinationPort(22);
        packet1.setFlags(new ArrayList<>(List.of(Flag.SYN)));
        packet1.setProtocol(Protocol.ICMP);


        PacketEvent packet2 = new PacketEvent();
        packet2.setSourceIP("172.16.1.0");
        packet2.setSourcePort(6053);
        packet2.setPayloadSize(400L);
        packet2.setDestinationIP("192.168.1.101");
        packet2.setDestinationPort(22);
        packet2.setFlags(new ArrayList<>(List.of(Flag.SYN)));
        packet2.setProtocol(Protocol.ICMP);

        PacketEvent packet3 = new PacketEvent();
        packet3.setSourceIP("172.16.1.0");
        packet3.setSourcePort(6054);
        packet3.setPayloadSize(400L);
        packet3.setDestinationIP("192.168.1.101");
        packet3.setDestinationPort(22);
        packet3.setFlags(new ArrayList<>(List.of(Flag.SYN)));
        packet3.setProtocol(Protocol.ICMP);

        PacketEvent packet4 = new PacketEvent();
        packet4.setSourceIP("172.16.1.0");
        packet4.setSourcePort(6055);
        packet4.setPayloadSize(400L);
        packet4.setDestinationIP("192.168.1.101");
        packet4.setDestinationPort(22);
        packet4.setFlags(new ArrayList<>(List.of(Flag.SYN)));
        packet4.setProtocol(Protocol.ICMP);


        PacketEvent packet5 = new PacketEvent();
        packet5.setSourceIP("172.16.1.0");
        packet5.setSourcePort(6055);
        packet5.setPayloadSize(400L);
        packet5.setDestinationIP("192.168.1.101");
        packet5.setDestinationPort(22);
        packet5.setFlags(new ArrayList<>(List.of(Flag.SYN)));
        packet5.setProtocol(Protocol.ICMP);


        PacketEvent packet6 = new PacketEvent();
        packet6.setSourceIP("172.16.1.0");
        packet6.setSourcePort(6055);
        packet6.setPayloadSize(400L);
        packet6.setDestinationIP("192.168.1.101");
        packet6.setDestinationPort(22);
        packet6.setFlags(new ArrayList<>(List.of(Flag.SYN)));
        packet6.setProtocol(Protocol.ICMP);

        return new ArrayList<>(Arrays.asList(packet1, packet2,packet3,packet4,packet5, packet6));
    }
}
