package edu.itmo.blps.model.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.itmo.blps.model.company.Company;
import edu.itmo.blps.model.user.User;
import edu.itmo.blps.model.device.Device;
import lombok.Data;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

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
	private User customer;

	@Min(0)
	private Integer amount;

	@Min(0)
	@Max(100)
	private Integer discount;

	@Basic
	@Column(name = "date_time")
	private LocalDateTime dateTime;

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

	@JsonProperty
	public Integer getCustomerId() {
		return customer.getId();
	}

	@JsonProperty
	public void setCustomerId(Integer sellerId) {
		customer = new User(sellerId);
	}
}
