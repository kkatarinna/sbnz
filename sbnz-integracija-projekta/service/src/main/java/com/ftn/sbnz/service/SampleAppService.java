package com.ftn.sbnz.service;

import com.ftn.sbnz.model.events.PacketEvent;
import com.ftn.sbnz.model.models.Device;
import com.ftn.sbnz.model.models.NetworkService;
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
    private final KieSession cepSession;

	@Autowired
	public SampleAppService(@Qualifier("fwKsession") KieSession fwSession,
                            @Qualifier("tempKsession") KieSession tempSession,
                            @Qualifier("cepKsession") KieSession cepSession) {
		this.fwSession = fwSession;
        this.tempSession = tempSession;
        this.cepSession = cepSession;


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

        this.cepSession.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                System.out.println("cep Pravilo aktivirano: " + event.getMatch().getRule().getName());
            }
        });

	}

	/**
	 * Ubacuje Device u sesiju, aktivira pravila i vraća informacije o sesiji
	 */
	public Map<String, Object> insertDeviceAndTrack(Device device) {
		Map<String, Object> response = new HashMap<>();
		List<String> firedRules = new ArrayList<>();

		// Lokalni listener za ovu operaciju
		fwSession.addEventListener(new DefaultAgendaEventListener() {
			@Override
			public void afterMatchFired(AfterMatchFiredEvent event) {
				firedRules.add(event.getMatch().getRule().getName());
			}
		});

		fwSession.insert(device);
		int count = fwSession.fireAllRules();

		response.put("count: ",count);
		response.put("firedRules", firedRules);
		response.put("sessionObjects", fwSession.getObjects());

		return response;
	}

	/**
	 * Ubacuje Service u sesiju, aktivira pravila i vraća informacije o sesiji
	 */
	public Map<String, Object> insertServiceAndTrack(NetworkService service) {
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

    public Map<String, Object> insertPacketAndTrack(PacketEvent packet) {
        Map<String, Object> response = new HashMap<>();
        List<String> firedRulesCEP = new ArrayList<>();

        cepSession.addEventListener(new DefaultAgendaEventListener() {
            @Override
            public void afterMatchFired(AfterMatchFiredEvent event) {
                firedRulesCEP.add(event.getMatch().getRule().getName());
            }
        });

        cepSession.insert(packet);
        int countCEP = cepSession.fireAllRules();
        response.put("countCep: ", countCEP);
        response.put("firedCEPRules", firedRulesCEP);
        response.put("CEPsessionObjects", cepSession.getObjects());
        return response;
    }
}
