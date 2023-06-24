package edu.itmo.blps.dao.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.itmo.blps.dao.company.Company;
import edu.itmo.blps.dao.user.User;
import edu.itmo.blps.dao.device.Device;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "device_id")
	@JsonIgnore
	private Device device;

	@ManyToOne
	@JoinColumn(name = "seller_id")
	@JsonIgnore
	private Company seller;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	@JsonIgnore
	private User user;

	private Integer amount;

	@JsonProperty
	public Integer getDeviceId() {
		return device.getId();
	}

	@JsonProperty
	public void setDeviceId(Integer deviceId) {
		device = new Device(deviceId);
	}

	@JsonProperty
	public Integer getSellerId() {
		return seller.getId();
	}

	@JsonProperty
	public void setSellerId(Integer sellerId) {
		seller = new Company(sellerId);
	}
}
