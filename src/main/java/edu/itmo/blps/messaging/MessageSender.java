package edu.itmo.blps.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Aspect;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

	@Value("${spring.rabbitmq.template.exchange}")
	private String exchange;

	@Value("${spring.rabbitmq.template.routing-key}")
	private String routingKey;

	@Value("${spring.mqtt.default.topic}")
	private String defaultTopic;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private MqttConnectOptions connectOptions;

	@Value("${spring.mqtt.url}") String hostUrl;

	@Value("${spring.mqtt.client.id}") String clientId;

//	private final IMqttClient client;

//	public MessageSender(@Value("${spring.mqtt.url}") String hostUrl,
//						@Value("${spring.mqtt.client.id}") String clientId) {
//		try {
//			client = new MqttClient(hostUrl, clientId);
//		} catch (MqttException e) {
//			throw new RuntimeException(e);
//		}
//	}

	@ServiceActivator(outputChannel = "mqttOutboundChannel")
	public void publish(Object arr) throws JsonProcessingException {
		publish(new MqttMessage(mapper.writeValueAsBytes(arr)));
	}

	@ServiceActivator(outputChannel = "mqttOutboundChannel")
	public void publish(MqttMessage message) {
		try (IMqttClient mqttClient = new MqttClient(hostUrl, clientId)){
			mqttClient.connect(connectOptions);
			mqttClient.publish(defaultTopic, message);
			mqttClient.disconnect();
		} catch (MqttException e) {
			throw new RuntimeException(e);
		}
	};
}
