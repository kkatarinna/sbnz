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
        ArrayList<PacketEvent> suspiciousPackets = generateSuspiciousPacket();
        Map<String, Object> response = new HashMap<>();
        List<String> firedRulesFW = new ArrayList<>();
        List<String> firedRulesTemp = new ArrayList<>();

        cep.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                firedRulesFW.add(event.getMatch().getRule().getName());
            }
        });


        for(PacketEvent packet : suspiciousPackets) {
            cep.insert(packet);

        }
        int countcep = cep.fireAllRules();

        if(countcep != 0) {
            response.put("countCep: ", countcep);
            response.put("firedCepRules", firedRulesFW);
            response.put("cepSessionObjects", cep.getObjects());
        }
        cep.getFactHandles().forEach(cep::delete);
        return response;
    }

    private static ArrayList<PacketEvent> generateSuspiciousPacket() {
        PacketEvent suspiciousIPPacket = new PacketEvent();
        suspiciousIPPacket.setSourceIP("172.16.1.1");
        suspiciousIPPacket.setSourcePort(6052);
        suspiciousIPPacket.setDestinationIP("192.168.1.100");
        suspiciousIPPacket.setDestinationPort(443);
        suspiciousIPPacket.setFlags(new ArrayList<>(List.of(Flag.SYN)));
        suspiciousIPPacket.setProtocol(Protocol.TCP);


        PacketEvent suspiciousPortPacket = new PacketEvent();
        suspiciousPortPacket.setSourceIP("172.16.1.1");
        suspiciousPortPacket.setSourcePort(6052);
        suspiciousPortPacket.setDestinationIP("192.168.1.101");
        suspiciousPortPacket.setDestinationPort(22);
        suspiciousPortPacket.setFlags(new ArrayList<>(List.of(Flag.SYN)));
        suspiciousPortPacket.setProtocol(Protocol.TCP);
        return new ArrayList<>(Arrays.asList(suspiciousIPPacket, suspiciousPortPacket));
    }
}
