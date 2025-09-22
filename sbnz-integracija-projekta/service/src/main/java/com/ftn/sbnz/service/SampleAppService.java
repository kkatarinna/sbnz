package com.ftn.sbnz.service;

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

	@Autowired
	public SampleAppService(@Qualifier("fwKsession") KieSession fwSession) {
		this.fwSession = fwSession;

		// Dodaj listener za sve sesije odmah pri kreiranju servisa
		this.fwSession.addEventListener(new DefaultAgendaEventListener() {
			@Override
			public void afterMatchFired(AfterMatchFiredEvent event) {
				System.out.println("Pravilo aktivirano: " + event.getMatch().getRule().getName());
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
		List<String> firedRules = new ArrayList<>();

		fwSession.addEventListener(new DefaultAgendaEventListener() {
			@Override
			public void afterMatchFired(AfterMatchFiredEvent event) {
				firedRules.add(event.getMatch().getRule().getName());
			}
		});

		fwSession.insert(service);
		int count = fwSession.fireAllRules();

		response.put("count: ",count);
		response.put("firedRules", firedRules);
		response.put("sessionObjects", fwSession.getObjects());

		return response;
	}

	/**
	 * Samo insert bez praćenja (stari API)
	 */
	public String insertDevice(Device device) {
		fwSession.insert(device);
		fwSession.fireAllRules();
		return "inserted device";
	}

	public String insertService(NetworkService service) {
		fwSession.insert(service);
		fwSession.fireAllRules();
		return "inserted service";
	}
}
