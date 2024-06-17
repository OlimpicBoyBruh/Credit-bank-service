package ru.bank.jd.configuration;

import lombok.Data;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Data
@Configuration
public class KafkaTopicConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public AdminClient adminClient() {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return AdminClient.create(config);
    }

    @Bean
    public List<NewTopic> createTopics(AdminClient adminClient) {
        List<NewTopic> newTopics = new ArrayList<>();

        newTopics.add(new NewTopic("finish-registration", 1, (short) 1));
        newTopics.add(new NewTopic("create-documents", 1, (short) 1));
        newTopics.add(new NewTopic("send-documents", 1, (short) 1));
        newTopics.add(new NewTopic("send-ses", 1, (short) 1));
        newTopics.add(new NewTopic("credit-issued", 1, (short) 1));
        newTopics.add(new NewTopic("statement-denied", 1, (short) 1));

        adminClient.createTopics(newTopics);
        return newTopics;
    }
}
