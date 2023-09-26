package edu.itmo.blps.messaging.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.itmo.blps.model.device.Device;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	property = "type")
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class DeviceDTO {
	private Integer id;
	private String name;
	private Integer price;

	public static DeviceDTO createFromDevice(Device device) {
		return builder()
				.name(device.getName())
				.id(device.getId())
				.price(device.getPrice())
				.build();
	}
}
