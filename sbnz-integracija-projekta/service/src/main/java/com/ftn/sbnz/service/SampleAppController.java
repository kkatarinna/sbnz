package com.ftn.sbnz.service;

import com.ftn.sbnz.model.models.Device;
import com.ftn.sbnz.model.models.NetworkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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
	public Map<String, Object> insertDevice(@RequestBody Device device) {

		Map<String, Object> result = sampleService.insertDeviceAndTrack(device);

		return result;
	}

	@RequestMapping(value = "/networkService",
			method = RequestMethod.POST,
			consumes = "application/json",
			produces = "application/json")
	public Map<String, Object> insertService(@RequestBody NetworkService networkService) {

		Map<String, Object> result = sampleService.insertServiceAndTrack(networkService);

		return result;
	}
	
	
	
}
