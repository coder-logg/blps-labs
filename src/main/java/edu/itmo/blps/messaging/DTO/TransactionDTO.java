package edu.itmo.blps.messaging.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.itmo.blps.model.company.Company;
import edu.itmo.blps.model.customer.Customer;
import edu.itmo.blps.model.transaction.Transaction;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.EXISTING_PROPERTY,
		property = "type",
		visible = true)
@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
public class TransactionDTO {
	private final String type = "TransactionDTO";
	private Integer id;
	private DeviceDTO device;
	private String sellerUsername;
	private String customerUsername;
	private String customerName;
	private Integer amount;
	private Integer discount;
	private LocalDateTime dateTime;
	private String sellerCompanyName;

	public static TransactionDTO createFromTransaction(Transaction transaction){
		String customerName = transaction.getCustomer() instanceof Customer ?
				((Customer)transaction.getCustomer()).getFirstName() + " " + ((Customer)transaction.getCustomer()).getFirstName() :
				((Company)transaction.getCustomer()).getName();
		return TransactionDTO.builder()
				.id(transaction.getId())
				.amount(transaction.getAmount())
				.sellerUsername(transaction.getSeller().getUsername())
				.customerUsername(transaction.getCustomer().getUsername())
				.discount(transaction.getDiscount())
				.device(DeviceDTO.createFromDevice(transaction.getDevice()))
				.dateTime(transaction.getDateTime())
				.sellerCompanyName(transaction.getSeller().getName())
				.customerName(customerName)
				.build();
	}
}
