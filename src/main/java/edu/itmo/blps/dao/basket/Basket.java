package edu.itmo.blps.dao.basket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.itmo.blps.dao.customer.Customer;
import edu.itmo.blps.dao.user.User;
import edu.itmo.blps.dao.device.Device;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "basket")
public class Basket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	@JsonIgnore
	private Customer customer;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(name = "devices_in_basket",
			joinColumns = @JoinColumn(name = "basket_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "device_id", referencedColumnName = "id"))
	private List<Device> devices;

	@JsonProperty
	public Basket setCustomerId(Integer id) {
		customer = new Customer(id);
		return this;
	}

	@JsonProperty
	public Integer getCustomerId() {
		if (customer != null)
			return customer.getId();
		return null;
	}

	public void addDevice(Device device) {
		if (Objects.isNull(devices))
			devices = new ArrayList<>();
		devices.add(device);
	}

	public void removeDevice(Device device) {
		if (!Objects.isNull(devices))
			devices.remove(device);
	}
}
