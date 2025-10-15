package com.ftn.sbnz.service;

import com.ftn.sbnz.model.models.Device;
import com.ftn.sbnz.model.models.NetworkService;
import com.ftn.sbnz.model.events.PacketEvent;
import com.ftn.sbnz.service.DTO.DeviceDTO;
import com.ftn.sbnz.service.DTO.NetworkServiceDTO;
import com.ftn.sbnz.service.DTO.PacketDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class SampleAppController {
	private static Logger log = LoggerFactory.getLogger(SampleAppController.class);

	private final SampleAppService sampleService;

	@Autowired
	public SampleAppController(SampleAppService sampleService) {
		this.sampleService = sampleService;
	}

	@RequestMapping(value = "/device",
			method = RequestMethod.POST,
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<Map<String, Object>> insertDevice() {
		Device dev1 = new Device(UUID.randomUUID(), "123.123.123.123", "Linux_mint", 4, "Linux");
		Device dev2 = new Device(UUID.randomUUID(), "122.241.21.52", "Windows", 10, "Windows");
		Device dev3 = new Device(UUID.randomUUID(), "185.10.10.1", "Linux_ubuntu", 4, "Linux");
		Device dev4 = new Device(UUID.randomUUID(), "193.55.33.8", "macOS", 4, "macOS");
		Device dev5 = new Device(UUID.randomUUID(), "10.0.0.77", "Linux_kali", 4, "Linux");
		List<Device> devices = new ArrayList<>();
		devices.add(dev1);
		devices.add(dev2);
		devices.add(dev3);
		devices.add(dev4);
		devices.add(dev5);
		Map<String, Object> result = sampleService.insertDeviceAndTrack(devices);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Device successfully inserted");
		response.put("devices", devices);
		if (result != null && !result.isEmpty()) {
			response.put("countTemp", result.get("countTemp"));
			response.put("firedTempRules", result.get("firedTempRules"));
			response.put("tempSessionObjects", result.get("TempsessionObjects"));
		}
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/networkService",
			method = RequestMethod.POST,
			consumes = "application/json",
			produces = "application/json")
	public Map<String, Object> insertService(@RequestBody NetworkServiceDTO networkService) {

		Map<String, Object> result = sampleService.insertServiceAndTrack(networkService);

		return result;
	}


    @RequestMapping(value = "/packet",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Map<String, Object> insertPacket(@RequestBody PacketDTO packet) {

        Map<String, Object> result = sampleService.insertPacketAndTrack(packet);

        return result;
    }


    @RequestMapping(value = "/scanDevice",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Map<String, Object>> scanDevice() {
        Map<String, Object> result = sampleService.scanDevice();
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/scanNetworkService",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Map<String, Object> scanNetworkService() {

        return sampleService.scanServices();
    }
    @RequestMapping(value = "/suspiciousPacket",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Map<String, Object> insertSuspiciousPacket() {

        return sampleService.insertSuspiciousPacket();
    }

    @RequestMapping(value = "/networkScanAttack",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Map<String, Object> networkScanAttack() {

        return sampleService.networkScanAttack();
    }

    @RequestMapping(value = "/synFlood",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Map<String, Object> synFlood() {

        return sampleService.synFlood();
    }

    @RequestMapping(value = "/distributedSynFlood",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Map<String, Object> distributedSynFlood() {

        return sampleService.distributedSynFlood();
    }

    @RequestMapping(value = "/dnsTunneling",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Map<String, Object> dnsTunneling() {

        return sampleService.dnsTunneling();
    }

    @RequestMapping(value = "/icmpTunneling",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Map<String, Object> icmpTunneling() {

        return sampleService.icmpTunneling();
    }

    @RequestMapping(value = "/outboundPortAbuse",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Map<String, Object> outboundPortAbuse() {

        return sampleService.outboundPortAbuse();
    }
}
