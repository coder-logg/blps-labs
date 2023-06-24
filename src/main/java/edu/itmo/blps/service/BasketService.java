package edu.itmo.blps.service;

import edu.itmo.blps.dao.basket.Basket;
import edu.itmo.blps.dao.basket.BasketRepository;
import edu.itmo.blps.dao.device.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;

@Service
public class BasketService {

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private BasketRepository basketRepository;

	@Transactional(value = "bitronixTransactionManager")
	public Basket saveCart(Basket basket) {
		return basketRepository.save(basket);
	}

	public Basket createBasketForCustomerId(Integer customerId){
		Basket newBasket = new Basket();
		newBasket.setCustomerId(customerId);
		return basketRepository.save(newBasket);
	}

	public Basket getBasketByUsernameOrThrow(String username) {
		return basketRepository.findByCustomer_Username(username)
				.orElseThrow(() -> new EntityNotFoundException("Cart was not found for this username=" + username));
	}

	public Basket findByUsername(String username) {
		return basketRepository.findByCustomer_Username(username)
				.orElseThrow(() -> new EntityNotFoundException("Cart was not found for this username=" + username));
	}

	@Transactional(value = "bitronixTransactionManager")
	public Basket putDeviceInBasket(Device device, String username) {
		Basket basket = findByUsername(username);
		basket.addDevice(deviceService.findDevice(device));
		return basketRepository.save(basket);
	}

	@Transactional(value = "bitronixTransactionManager")
	public Basket removeDeviceFromBasket(Device device, String username) {
		device = deviceService.findDevice(device);
		Basket basket = findByUsername(username);
		if (basket.getDevices().contains(device)) {
			basket.removeDevice(device);
			return basketRepository.save(basket);
		}
		else
			throw new EntityNotFoundException("Cart doesn't contain device: " + device);
	}
	
	
	public boolean existDeviceInBasket(Integer deviceId, String username) {
		return findByUsername(username).getDevices().contains(deviceService.getDevice(deviceId));
	}

	public Optional<Device> getDeviceIfPresentInBasket(Integer deviceId, String username) {
		Device deviceFromDb = deviceService.getDevice(deviceId);
		if (Objects.isNull(deviceFromDb))
			return Optional.empty();
		Basket basket = findByUsername(username);
		if (basket.getDevices().contains(deviceFromDb))
			return Optional.of(deviceFromDb);
		return Optional.empty();
	}

	@Transactional(value = "bitronixTransactionManager")
	public Basket removeDeviceFromBasket(Integer deviceId, String username) {
		Device device = deviceService.getDeviceOrThrow(deviceId);
		Basket basket = findByUsername(username);
		if (basket.getDevices().contains(device)) {
			basket.removeDevice(device);
			return basketRepository.save(basket);
		}
		else
			throw new EntityNotFoundException("Cart doesn't contain device: " + deviceId);
	}
}
