package com.ftn.sbnz.service.cep1Attacks;

import com.ftn.sbnz.model.enums.Protocol;
import com.ftn.sbnz.model.events.PacketEvent;

import java.util.*;

public class NetworkScanAttack {


        public static ArrayList<PacketEvent> generateManyIPDestinationsSameSource() {
        PacketEvent packet1 = new PacketEvent();
        packet1.setSourceIP("172.16.1.1");
        packet1.setSourcePort(6052);
        packet1.setDestinationIP("192.168.1.100");
        packet1.setDestinationPort(22);
        packet1.setFlags(new ArrayList<>(List.of()));
        packet1.setProtocol(Protocol.TCP);


        PacketEvent packet2 = new PacketEvent();
        packet2.setSourceIP("172.16.1.1");
        packet2.setSourcePort(6053);
        packet2.setDestinationIP("192.168.1.101");
        packet2.setDestinationPort(22);
        packet2.setFlags(new ArrayList<>(List.of()));
        packet2.setProtocol(Protocol.TCP);

        PacketEvent packet3 = new PacketEvent();
        packet3.setSourceIP("172.16.1.1");
        packet3.setSourcePort(6054);
        packet3.setDestinationIP("192.168.1.102");
        packet3.setDestinationPort(22);
        packet3.setFlags(new ArrayList<>(List.of()));
        packet3.setProtocol(Protocol.TCP);

        PacketEvent packet4 = new PacketEvent();
        packet4.setSourceIP("172.16.1.1");
        packet4.setSourcePort(6055);
        packet4.setDestinationIP("192.168.1.103");
        packet4.setDestinationPort(22);
        packet4.setFlags(new ArrayList<>(List.of()));
        packet4.setProtocol(Protocol.TCP);

        return new ArrayList<>(Arrays.asList(packet1, packet2,packet3,packet4));
    }
}
