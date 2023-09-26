package edu.itmo.blps.controller;

import edu.itmo.blps.model.basket.Basket;
import edu.itmo.blps.model.device.Device;
import edu.itmo.blps.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/{username}/basket")
@RestController
public class BasketController {

	@Autowired
	private BasketService basketService;

	@GetMapping
	@PreAuthorize("#username == authentication.name")
	public ResponseEntity<Basket> getBasket(@PathVariable String username){
		return ResponseEntity.ok(basketService.getBasketByUsernameOrThrow(username));
	}

	@PutMapping("/put-device")
	@PreAuthorize("#username == authentication.name")
	public ResponseEntity<Basket> putDeviceToBasket(@PathVariable String username, @RequestParam Integer deviceId) {
		return ResponseEntity.ok(basketService.putDeviceInBasket(new Device(deviceId), username));
	}

	@DeleteMapping("/remove-device/{deviceId}")
	@PreAuthorize("#username == authentication.name")
	public ResponseEntity<Basket> removeDeviceFromBasket(@PathVariable Integer deviceId, @PathVariable String username) {
		return ResponseEntity.ok(basketService.removeDeviceFromBasket(deviceId, username));
	}
}
