package com.ftn.sbnz.service;

import com.ftn.sbnz.model.events.Alert;
import com.ftn.sbnz.model.events.PacketEvent;
import com.ftn.sbnz.model.models.*;
import com.ftn.sbnz.model.models.Log;
import com.ftn.sbnz.service.DTO.NetworkServiceDTO;
import com.ftn.sbnz.service.DTO.PacketDTO;
import com.ftn.sbnz.service.cep1Attacks.*;
import com.ftn.sbnz.service.Mapper.DeviceMapper;
import com.ftn.sbnz.service.Mapper.NetworkServiceMapper;
import com.ftn.sbnz.service.Mapper.PacketMapper;
import com.ftn.sbnz.service.cep2Attacks.DnsTunneling;
import com.ftn.sbnz.service.cep2Attacks.IcmpTunneling;
import com.ftn.sbnz.service.cep2Attacks.OutBoundPortAbuse;
import com.ftn.sbnz.service.fwChain.InsertLogs;
import com.ftn.sbnz.service.insert.InsertDevices;
import com.ftn.sbnz.service.insert.InsertServices;
import com.ftn.sbnz.service.sessionUtils.SessionUtils;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class SampleAppService {

	private final KieSession fwSession;
    private final KieSession tempSession;
    private final KieSession tempSession2;
    private final KieSession cepSession;
    private final KieSession cepSession2;

    private final DeviceMapper deviceMapper;

    private final NetworkServiceMapper networkServiceMapper;

    private final PacketMapper packetMapper;

	@Autowired
	public SampleAppService(@Qualifier("fwKsession") KieSession fwSession,
                            @Qualifier("tempKsession") KieSession tempSession,
                            @Qualifier("tempKsession2") KieSession tempSession2,
                            @Qualifier("cepKsession") KieSession cepSession,
                            @Qualifier("cepKsession2") KieSession cepSession2,
                            DeviceMapper deviceMapper,
                            NetworkServiceMapper networkServiceMapper,
                            PacketMapper packetMapper) {
		this.fwSession = fwSession;
        this.tempSession = tempSession;
        this.tempSession2 = tempSession2;
        this.cepSession = cepSession;
        this.cepSession2 = cepSession2;
        this.deviceMapper = deviceMapper;
        this.networkServiceMapper = networkServiceMapper;
        this.packetMapper = packetMapper;


        // Dodaj listener za sve sesije odmah pri kreiranju servisa
		this.fwSession.addEventListener(new DefaultAgendaEventListener() {
			@Override
			public void afterMatchFired(AfterMatchFiredEvent event) {
				System.out.println("FW Pravilo aktivirano: " + event.getMatch().getRule().getName());
			}
		});
        this.tempSession.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                System.out.println("Temp Pravilo aktivirano: " + event.getMatch().getRule().getName());
            }
        });

        this.tempSession2.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                System.out.println("Temp Pravilo aktivirano: " + event.getMatch().getRule().getName());
            }
        });

        this.cepSession.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                System.out.println("cep Pravilo aktivirano: " + event.getMatch().getRule().getName());
            }
        });

        this.cepSession2.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                System.out.println("cep Pravilo aktivirano: " + event.getMatch().getRule().getName());
            }
        });

	}

	/**
	 * Ubacuje Device u sesiju, aktivira pravila i vraća informacije o sesiji
	 */
	public Map<String, Object> insertDeviceAndTrack(List<Device> device) {
		Map<String, Object> response = new HashMap<>();
		List<String> firedRules = new ArrayList<>();

        tempSession2.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                firedRules.add(event.getMatch().getRule().getName());
            }
        });

        for (Device d : device) {
            tempSession2.insert(d);
        }
        int countTemp = tempSession2.fireAllRules();

        if(countTemp != 0) {
            response.put("countTemp", countTemp);
            response.put("firedTempRules", firedRules);
            response.put("TempsessionObjects", tempSession2.getObjects());
        }
        System.out.println("COUNT TEMP"+response.get("countTemp"));
		return response;
	}

	/**
	 * Ubacuje Service u sesiju, aktivira pravila i vraća informacije o sesiji
	 */
	public Map<String, Object> insertServiceAndTrack(NetworkServiceDTO serviceDTO) {
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

        NetworkService service = networkServiceMapper.toEntity(serviceDTO);

		fwSession.insert(service);
		int countFW = fwSession.fireAllRules();
        tempSession.insert(service);
        int countTemp = tempSession.fireAllRules();
        if(countFW != 0) {
            response.put("countFW: ", countFW);
            response.put("firedFWRules", firedRulesFW);
            response.put("FWsessionObjects", fwSession.getObjects());
        }
        if(countTemp != 0) {
            response.put("countTemp: ", countTemp);
            response.put("firedTempRules", firedRulesTemp);
            response.put("TempsessionObjects", tempSession.getObjects());
        }
		return response;
	}



    public Map<String, Object> insertPacketAndTrack(PacketDTO packetDTO) {
        Map<String, Object> response = new HashMap<>();
        List<String> firedRulesCEP = new ArrayList<>();
        List<String> firedRulesCEP2 = new ArrayList<>();

        cepSession.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                firedRulesCEP.add(event.getMatch().getRule().getName());
            }
        });

        cepSession2.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                firedRulesCEP2.add(event.getMatch().getRule().getName());
            }
        });

        PacketEvent packet = packetMapper.toEntity(packetDTO);
        cepSession.insert(packet);
        cepSession2.insert(packet);

        int countCEP = cepSession.fireAllRules();
        int countCEP2 = cepSession2.fireAllRules();

        response.put("CEPsessionObjects", cepSession.getObjects());
        response.put("CEPsession2Objects", cepSession2.getObjects());

        if(countCEP + countCEP2 != 0){
            List<String> allFiredRules = new ArrayList<>(firedRulesCEP);
            allFiredRules.addAll(firedRulesCEP2);

            List<PacketEvent> allPackets = new ArrayList<>(SessionUtils.getPacketEvents(cepSession));
            allPackets.addAll(SessionUtils.getPacketEvents(cepSession2));

            List<Alert> allAlerts = new ArrayList<>(SessionUtils.getAlerts(cepSession));
            allAlerts.addAll(SessionUtils.getAlerts(cepSession2));

            List<Alert> allVulns = new ArrayList<>(SessionUtils.getAlerts(cepSession));
            allAlerts.addAll(SessionUtils.getAlerts(cepSession2));


            List<Alert> allReccom = new ArrayList<>(SessionUtils.getAlerts(cepSession));
            allAlerts.addAll(SessionUtils.getAlerts(cepSession2));


            response.put("count: ", countCEP + countCEP2);
            response.put("firedRules", allFiredRules);
            response.put("Packets", allPackets);
            response.put("Alerts", allAlerts);
            response.put("Vulnerabilities", allVulns);
            response.put("Recommendations", allReccom);
        }
        return response;
    }

    public Map<String, Object> scanDevice() {
        this.tempSession2.getFactHandles().forEach(this.tempSession2::delete);
        List<Device> devices = InsertDevices.generateDevices();
        Map<String, Object> response = new HashMap<>();
        List<String> firedRulesTemp = new ArrayList<>();

        tempSession2.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                firedRulesTemp.add(event.getMatch().getRule().getName());
            }
        });

        for(Device device : devices) {
            tempSession2.insert(device);
        }

        int countTemp = tempSession2.fireAllRules();

        response.put("count: ",  countTemp);
        response.put("firedRules", firedRulesTemp);
        response.put("Devices", SessionUtils.getDevices(this.tempSession2));
        response.put("Alerts", SessionUtils.getAlerts(this.tempSession2));
        response.put("Vulnerabilities", SessionUtils.getVulnerability(this.tempSession2));
        response.put("Recommendations", SessionUtils.getRecommendations(this.tempSession2));

        return response;
    }

    public  Map<String, Object> scanServices(){
        this.fwSession.getFactHandles().forEach(this.fwSession::delete);
        this.tempSession.getFactHandles().forEach(this.tempSession::delete);
        List<NetworkService> services = InsertServices.generateNetworkServices();
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

        if(countFW + countTemp != 0){

            List<String> allFiredRules = new ArrayList<>(firedRulesTemp);
            allFiredRules.addAll(firedRulesFW);

            List<NetworkService> allNetworkServices = new ArrayList<>(SessionUtils.getNetworkServices(tempSession));

            List<Alert> allAlerts = new ArrayList<>(SessionUtils.getAlerts(tempSession));
            allAlerts.addAll(SessionUtils.getAlerts(fwSession));

            List<Vulnerability> allVulns = new ArrayList<>(SessionUtils.getVulnerability(tempSession));
            allVulns.addAll(SessionUtils.getVulnerability(fwSession));


            List<Recommendation> allReccom = new ArrayList<>(SessionUtils.getRecommendations(tempSession));
            allReccom.addAll(SessionUtils.getRecommendations(fwSession));

            response.put("count: ", countFW + countTemp);
            response.put("firedRules", allFiredRules);
            response.put("NetworkServices", allNetworkServices);
            response.put("Alerts", allAlerts);
            response.put("Vulnerabilities", allVulns);
            response.put("Recommendations", allReccom);
        }
        return response;
    }

//        return InsertServices.insertServicesAndTrack(this.tempSession2, this.fwSession);


    public Map<String,Object> insertSuspiciousPacket(){
        this.cepSession.getFactHandles().forEach(this.cepSession::delete);
        ArrayList<PacketEvent> suspiciousPackets = InsertSuspiciousPackets.generateSuspiciousPacket();
        Map<String, Object> response = new HashMap<>();
        List<String> fiderRulesCep = new ArrayList<>();

        this.cepSession.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                fiderRulesCep.add(event.getMatch().getRule().getName());
            }
        });


        for(PacketEvent packet : suspiciousPackets) {
            this.cepSession.insert(packet);

        }
        int countcep = this.cepSession.fireAllRules();

        if(countcep != 0) {
            response.put("count: ", countcep);
            response.put("firedRules", fiderRulesCep);
            response.put("Packets", SessionUtils.getPacketEvents(this.cepSession));
            response.put("Alerts", SessionUtils.getAlerts(this.cepSession));
            response.put("Vulnerabilities", SessionUtils.getVulnerability(this.cepSession));
            response.put("Recommendations", SessionUtils.getRecommendations(this.cepSession));
        }
        return response;
    }


    public Map<String, Object> networkScanAttack() {
        this.cepSession.getFactHandles().forEach(this.cepSession::delete);
        ArrayList<PacketEvent> suspiciousPackets = NetworkScanAttack.generateManyIPDestinationsSameSource();
        Map<String, Object> response = new HashMap<>();
        List<String> fiderRulesCep = new ArrayList<>();

        this.cepSession.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                fiderRulesCep.add(event.getMatch().getRule().getName());
            }
        });


        for(PacketEvent packet : suspiciousPackets) {
            this.cepSession.insert(packet);

        }
        int countcep = this.cepSession.fireAllRules();

        if(countcep != 0) {
            response.put("count: ", countcep);
            response.put("firedRules", fiderRulesCep);
            response.put("Packets", SessionUtils.getPacketEvents(this.cepSession));
            response.put("Alerts", SessionUtils.getAlerts(this.cepSession));
            response.put("Vulnerabilities", SessionUtils.getVulnerability(this.cepSession));
            response.put("Recommendations", SessionUtils.getRecommendations(this.cepSession));
        }
        return response;
    }

    public Map<String, Object> synFlood() {
        this.cepSession.getFactHandles().forEach(this.cepSession::delete);
        ArrayList<PacketEvent> suspiciousPackets = SynFlood.generateSynFlood();
        Map<String, Object> response = new HashMap<>();
        List<String> fiderRulesCep = new ArrayList<>();

        this.cepSession.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                fiderRulesCep.add(event.getMatch().getRule().getName());
            }
        });


        for(PacketEvent packet : suspiciousPackets) {
            this.cepSession.insert(packet);

        }
        int countcep = this.cepSession.fireAllRules();

        if(countcep != 0) {
            response.put("count: ", countcep);
            response.put("firedRules", fiderRulesCep);
            response.put("Packets", SessionUtils.getPacketEvents(this.cepSession));
            response.put("Alerts", SessionUtils.getAlerts(this.cepSession));
            response.put("Vulnerabilities", SessionUtils.getVulnerability(this.cepSession));
            response.put("Recommendations", SessionUtils.getRecommendations(this.cepSession));
        }
        return response;
    }

    public Map<String, Object> distributedSynFlood() {
        this.cepSession.getFactHandles().forEach(this.cepSession::delete);
        ArrayList<PacketEvent> suspiciousPackets = DistributedSynFlood.generateSynFlood();
        Map<String, Object> response = new HashMap<>();
        List<String> fiderRulesCep = new ArrayList<>();

        this.cepSession.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                fiderRulesCep.add(event.getMatch().getRule().getName());
            }
        });


        for(PacketEvent packet : suspiciousPackets) {
            this.cepSession.insert(packet);

        }
        int countcep = this.cepSession.fireAllRules();

        if(countcep != 0) {
            response.put("count: ", countcep);
            response.put("firedRules", fiderRulesCep);
            response.put("Packets", SessionUtils.getPacketEvents(this.cepSession));
            response.put("Alerts", SessionUtils.getAlerts(this.cepSession));
            response.put("Vulnerabilities", SessionUtils.getVulnerability(this.cepSession));
            response.put("Recommendations", SessionUtils.getRecommendations(this.cepSession));
        }
        return response;
    }

    public Map<String, Object> dnsTunneling() {
        this.cepSession2.getFactHandles().forEach(this.cepSession2::delete);
        ArrayList<PacketEvent> suspiciousPackets = DnsTunneling.generateSuspiciousPackets();
        Map<String, Object> response = new HashMap<>();
        List<String> fiderRulesCep = new ArrayList<>();

        this.cepSession2.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                fiderRulesCep.add(event.getMatch().getRule().getName());
            }
        });


        for(PacketEvent packet : suspiciousPackets) {
            this.cepSession2.insert(packet);

        }
        int countcep = this.cepSession2.fireAllRules();

        if(countcep != 0) {
            response.put("count: ", countcep);
            response.put("firedRules", fiderRulesCep);
            response.put("Packets", SessionUtils.getPacketEvents(this.cepSession2));
            response.put("Alerts", SessionUtils.getAlerts(this.cepSession2));
            response.put("Vulnerabilities", SessionUtils.getVulnerability(this.cepSession2));
            response.put("Recommendations", SessionUtils.getRecommendations(this.cepSession2));
        }
        return response;
    }

    public Map<String, Object> icmpTunneling() {
        this.cepSession2.getFactHandles().forEach(this.cepSession2::delete);
        ArrayList<PacketEvent> suspiciousPackets = IcmpTunneling.generateSuspiciousPackets();
        Map<String, Object> response = new HashMap<>();
        List<String> fiderRulesCep = new ArrayList<>();

        this.cepSession2.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                fiderRulesCep.add(event.getMatch().getRule().getName());
            }
        });


        for(PacketEvent packet : suspiciousPackets) {
            this.cepSession2.insert(packet);

        }
        int countcep = this.cepSession2.fireAllRules();

        if(countcep != 0) {
            response.put("count: ", countcep);
            response.put("firedRules", fiderRulesCep);
            response.put("Packets", SessionUtils.getPacketEvents(this.cepSession2));
            response.put("Alerts", SessionUtils.getAlerts(this.cepSession2));
            response.put("Vulnerabilities", SessionUtils.getVulnerability(this.cepSession2));
            response.put("Recommendations", SessionUtils.getRecommendations(this.cepSession2));
        }
        return response;
    }

    public Map<String, Object> outboundPortAbuse() {
        this.cepSession2.getFactHandles().forEach(this.cepSession2::delete);
        ArrayList<PacketEvent> suspiciousPackets = OutBoundPortAbuse.generateSuspiciousPackets();
        Map<String, Object> response = new HashMap<>();
        List<String> fiderRulesCep = new ArrayList<>();

        this.cepSession2.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                fiderRulesCep.add(event.getMatch().getRule().getName());
            }
        });


        for(PacketEvent packet : suspiciousPackets) {
            this.cepSession2.insert(packet);

        }
        int countcep = this.cepSession2.fireAllRules();

        if(countcep != 0) {
            response.put("count: ", countcep);
            response.put("firedRules", fiderRulesCep);
            response.put("Packets", SessionUtils.getPacketEvents(this.cepSession2));
            response.put("Alerts", SessionUtils.getAlerts(this.cepSession2));
            response.put("Vulnerabilities", SessionUtils.getVulnerability(this.cepSession2));
            response.put("Recommendations", SessionUtils.getRecommendations(this.cepSession2));
        }
        return response;
    }

    public Map<String, Object> insertLogs() {
        ArrayList<Log> logs = InsertLogs.generateLogs();
        Map<String, Object> response = new HashMap<>();
        List<String> firedRules = new ArrayList<>();

        this.fwSession.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                firedRules.add(event.getMatch().getRule().getName());
            }
        });

        for(Log log : logs) {
            this.fwSession.insert(log);

        }
        int count = this.fwSession.fireAllRules();

        response.put("count: ", count);
        response.put("firedRules", firedRules);
        response.put("Packets", SessionUtils.getLogs(this.fwSession));
        response.put("Alerts", SessionUtils.getAlerts(this.fwSession));
        response.put("Recommendations", SessionUtils.getRecommendations(this.fwSession));
        return response;

    }

}
