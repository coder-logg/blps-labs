package edu.itmo.blps.dao.device;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.itmo.blps.dao.company.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "device")
public class Device {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String type;

	@Column(nullable = false, name = "name")
	private String name;

	private String description;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@Column(nullable = false,name="price")
	private Integer price;

	public Device(Integer id) {
		this.id = id;
	}
}
