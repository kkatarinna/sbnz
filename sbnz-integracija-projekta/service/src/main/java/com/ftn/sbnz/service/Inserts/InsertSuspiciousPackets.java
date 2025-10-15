package com.ftn.sbnz.service.Inserts;

import com.ftn.sbnz.model.enums.Flag;
import com.ftn.sbnz.model.enums.Protocol;
import com.ftn.sbnz.model.events.PacketEvent;
import com.ftn.sbnz.model.models.NetworkService;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.runtime.KieSession;

import java.util.*;

public class InsertSuspiciousPackets {

    public static Map<String, Object> insertSuspiciousPackets(KieSession cep){
    }

    public static ArrayList<PacketEvent> generateSuspiciousPacket() {
        PacketEvent suspiciousIPPacket = new PacketEvent();
        suspiciousIPPacket.setSourceIP("172.16.1.1");
        suspiciousIPPacket.setSourcePort(6052);
        suspiciousIPPacket.setDestinationIP("192.168.1.100");
        suspiciousIPPacket.setDestinationPort(443);
        suspiciousIPPacket.setFlags(new ArrayList<>(List.of(Flag.SYN)));
        suspiciousIPPacket.setProtocol(Protocol.TCP);


        PacketEvent suspiciousPortPacket = new PacketEvent();
        suspiciousPortPacket.setSourceIP("172.16.1.2");
        suspiciousPortPacket.setSourcePort(6052);
        suspiciousPortPacket.setDestinationIP("192.168.1.101");
        suspiciousPortPacket.setDestinationPort(22);
        suspiciousPortPacket.setFlags(new ArrayList<>(List.of(Flag.SYN)));
        suspiciousPortPacket.setProtocol(Protocol.TCP);
        return new ArrayList<>(Arrays.asList(suspiciousIPPacket, suspiciousPortPacket));
    }
}
