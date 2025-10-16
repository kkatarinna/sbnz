package com.ftn.sbnz.service.sessionUtils;

import com.ftn.sbnz.model.events.Alert;
import com.ftn.sbnz.model.events.PacketEvent;
import com.ftn.sbnz.model.models.*;
import org.kie.api.runtime.KieSession;

import java.util.List;
import java.util.stream.Collectors;

public class SessionUtils {
    // Generička funkcija za dobijanje svih facts određenog tipa
    public static <T> List<T> getFactsOfType(KieSession session, Class<T> type) {
        return session.getObjects()
                .stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    // Specifične funkcije za tvoje tipove
    public static List<PacketEvent> getPacketEvents(KieSession session) {
        return getFactsOfType(session, PacketEvent.class);
    }

    public static List<NetworkService> getNetworkServices(KieSession session) {
        return getFactsOfType(session, NetworkService.class);
    }

    public static List<Recommendation> getRecommendations(KieSession session) {
        return getFactsOfType(session, Recommendation.class);
    }

    public static List<Alert> getAlerts(KieSession session) {
        return getFactsOfType(session, Alert.class);
    }

    public static List<Device> getDevices(KieSession session) {
        return getFactsOfType(session, Device.class);
    }
    public static List<Vulnerability> getVulnerability(KieSession session) {
        return getFactsOfType(session, Vulnerability.class);
    }

    public static List<Log> getLogs(KieSession session) {
        return getFactsOfType(session, Log.class);
    }
}
