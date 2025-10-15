package com.ftn.sbnz.service;

import com.ftn.sbnz.model.events.PacketEvent;
import com.ftn.sbnz.model.models.Device;
import com.ftn.sbnz.model.models.NetworkService;
import com.ftn.sbnz.service.DTO.DeviceDTO;
import com.ftn.sbnz.service.DTO.NetworkServiceDTO;
import com.ftn.sbnz.service.DTO.PacketDTO;
import com.ftn.sbnz.service.Mapper.DeviceMapper;
import com.ftn.sbnz.service.Mapper.NetworkServiceMapper;
import com.ftn.sbnz.service.Mapper.PacketMapper;
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
        response.put("countCep: ", countCEP + countCEP2);
        response.put("firedCEPRules", firedRulesCEP);
        response.put("firedCEP2Rules", firedRulesCEP2);
        response.put("CEPsessionObjects", cepSession.getObjects());
        response.put("CEPsession2Objects", cepSession2.getObjects());
        return response;
    }
}
