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
		Device dev1 = new Device(UUID.randomUUID(), "123.123.123.123", "Linux_mint", "4", "Linux");
		Device dev2 = new Device(UUID.randomUUID(), "122.241.21.52", "Windows_11", "10", "Windows");
		List<Device> devices = new ArrayList<>();
		devices.add(dev1);
		devices.add(dev2);
		Map<String, Object> result = sampleService.insertDeviceAndTrack(devices);

		Map<String, Object> response = new HashMap<>();
		response.put("message", "Device successfully inserted");
		response.put("devices", devices);
		response.put("serviceResult", result);
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

    @RequestMapping(value = "/scanNestworkService",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Map<String, Object> scanNestowrkService() {

        return sampleService.insertServicesAndTrack();
    }

    @RequestMapping(value = "/packet",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Map<String, Object> insertPacket(@RequestBody PacketDTO packet) {

        Map<String, Object> result = sampleService.insertPacketAndTrack(packet);

        return result;
    }
	
	
	
}
