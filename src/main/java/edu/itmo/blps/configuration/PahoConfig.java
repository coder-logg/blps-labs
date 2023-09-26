package edu.itmo.blps.configuration;

//import edu.itmo.blps.service.MqttReceiveHandle;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;


@Slf4j
@Configuration
@IntegrationComponentScan
public class PahoConfig {

	@Value("${spring.mqtt.username}")
	private String username;

	@Value("${spring.mqtt.password}")
	private String password;

	@Value("${spring.mqtt.url}")
	private String hostUrl;

	@Value("${spring.mqtt.client.id}")
	private String clientId;

	@Value("${spring.mqtt.default.topic}")
	private String defaultTopic;

	@Value("${spring.mqtt.completionTimeout}")
	private Integer completionTimeout;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Bean(value = "connectionOptions")
	public MqttConnectOptions getMqttConnectOptions() {
		MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
		mqttConnectOptions.setUserName(username);
		mqttConnectOptions.setPassword(password.toCharArray());
		mqttConnectOptions.setServerURIs(new String[]{hostUrl});
		return mqttConnectOptions;
	}

	@Bean
	public MqttPahoClientFactory mqttClientFactory(MqttConnectOptions connectOptions) {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		factory.setConnectionOptions(connectOptions);
		return factory;
	}

	@Bean
	public MessageChannel mqttOutboundChannel() {
		return new DirectChannel();
	}

	@Bean
	public MessageProducer inbound(MqttPahoClientFactory clientFactory) {
		MqttPahoMessageDrivenChannelAdapter adapter =
				new MqttPahoMessageDrivenChannelAdapter(clientId, clientFactory, defaultTopic.trim().split(","));
		adapter.setCompletionTimeout(completionTimeout);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(0);
		adapter.setOutputChannel(mqttOutboundChannel());
		return adapter;
	}

//	@Bean
//	public Queue queue() {
//		return new Queue("mqtt-queue");
//	}
//
//	@Bean
//	public Binding binding() {
//		return new Binding("mqtt-queue", Binding.DestinationType.QUEUE, "amq.topic", "mailbox", null);
//	}
}

