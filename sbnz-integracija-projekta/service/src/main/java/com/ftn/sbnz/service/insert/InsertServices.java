package com.ftn.sbnz.service.insert;

import com.ftn.sbnz.model.enums.LogTag;
import com.ftn.sbnz.model.enums.LogType;
import com.ftn.sbnz.model.models.Device;
import com.ftn.sbnz.model.models.Log;
import com.ftn.sbnz.model.models.NetworkService;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.runtime.KieSession;

import java.util.*;

public class InsertServices {

    public static Map<String, Object> insertServicesAndTrack(KieSession fwSession, KieSession tempSession) {
        List<NetworkService> services = generateNetworkServices();
        Map<String, Object> response = new HashMap<>();
        List<String> firedRulesFW = new ArrayList<>();
        List<String> firedRulesTemp = new ArrayList<>();

        fwSession.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                firedRulesFW.add(event.getMatch().getRule().getName());
            }
        });

        tempSession.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                firedRulesTemp.add(event.getMatch().getRule().getName());
            }
        });

        for(NetworkService service : services) {
            fwSession.insert(service);
            tempSession.insert(service);
        }
        int countFW = fwSession.fireAllRules();
        int countTemp = tempSession.fireAllRules();

        response.put("countFW: ", countFW);
        response.put("firedFWRules", firedRulesFW);
        response.put("FWsessionObjects", fwSession.getObjects());

        response.put("countTemp: ", countTemp);
        response.put("firedTempRules", firedRulesTemp);
        response.put("TempsessionObjects", tempSession.getObjects());

        return response;
    }

    public static List<NetworkService> generateNetworkServices() {
        List<Device> devices = GenerateDevices();

        NetworkService apache = new NetworkService();
        apache.setId(UUID.randomUUID());
        apache.setDevice(devices.get(0));
        apache.setName("Apache HTTP Server");
        apache.setPort(80);
        apache.setVersion("2.0.65");

        NetworkService ssh = new NetworkService();
        ssh.setId(UUID.randomUUID());
        ssh.setDevice(devices.get(1));
        ssh.setName("ssh / MINA SSHD");
        ssh.setPort(22);
        ssh.setVersion("2.9.1");

        NetworkService http = new NetworkService();
        http.setId(UUID.randomUUID());
        http.setDevice(devices.get(0));
        http.setName("http");
        http.setPort(80);
        http.setVersion("1.3.42");

        NetworkService ssh2 = new NetworkService();
        ssh2.setId(UUID.randomUUID());
        ssh2.setDevice(devices.get(0));
        ssh2.setName("ssh");
        ssh2.setPort(22);
        ssh2.setVersion("2.4.56");

        return new ArrayList<>(Arrays.asList(apache, ssh, http, ssh2));
    }

    public static List<Device> GenerateDevices() {

        Device linuxMint22 = new Device();
        linuxMint22.setId(UUID.randomUUID());
        linuxMint22.setIp("172.16.1.1");
        linuxMint22.setOsName("Linux mint");
        linuxMint22.setOsType("Linux");
        linuxMint22.setOsVersion(22);

        Device linuxUbuntu10 = new Device();
        linuxUbuntu10.setId(UUID.randomUUID());
        linuxUbuntu10.setIp("172.16.1.2");
        linuxUbuntu10.setOsName("Linux Ubuntu");
        linuxUbuntu10.setOsType("Linux");
        linuxUbuntu10.setOsVersion(25);


        Device windows11 = new Device();
        windows11.setId(UUID.randomUUID());
        windows11.setIp("172.16.1.3");
        windows11.setOsName("Windows");
        windows11.setOsType("Windows");
        windows11.setOsVersion(11);
        return new ArrayList<>(Arrays.asList(linuxUbuntu10, linuxMint22, windows11));
    }
}
