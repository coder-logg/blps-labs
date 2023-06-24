package edu.itmo.blps.controller;

import edu.itmo.blps.dao.company.Company;
import edu.itmo.blps.dao.device.Device;
import edu.itmo.blps.dao.user.User;
import edu.itmo.blps.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/device")
@RestController
public class DeviceController {
	@Autowired
	private DeviceService deviceService;

	@GetMapping("/all")
	public ResponseEntity<List<Device>> getAllDevices(@RequestParam(defaultValue = "0") Integer pageNo,
													  @RequestParam(defaultValue = "10") Integer pageSize,
													  @RequestParam(defaultValue = "name") String sortBy) {
		return ResponseEntity.ok(deviceService.getAllDevices(pageNo, pageSize, sortBy));
	}

	@GetMapping("/{deviceId}")
	public ResponseEntity<Device> getDevice(@PathVariable Integer deviceId) {
		return ResponseEntity.ok(deviceService.getDevice(deviceId));
	}

	@GetMapping("/all-in-company/{companyId}")
	public ResponseEntity<List<Device>> getAllByCompany(@PathVariable Integer companyId) {
		return ResponseEntity.ok(deviceService.getAllByCompanyId(companyId));
	}

	@PostMapping
	@PreAuthorize("hasRole('COMPANY')")
	public ResponseEntity<Device> addDevice(@RequestBody Device device, Authentication authentication) {
		device.setCompany(new Company(((User) authentication.getPrincipal()).getId()));
		return ResponseEntity.ok(deviceService.saveDevice(device));
	}
}
