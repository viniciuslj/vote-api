package viniciuslj.vote.api.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@ConditionalOnProperty(value = "kafka.enable", havingValue = "true", matchIfMissing = true)
public class KafkaConfig {

    @Value(value = "${kafka.results.topic.name}")
    private String resultsTopicName;

    @Bean
    public NewTopic resultsTopic() {
        return TopicBuilder.name(resultsTopicName)
                .partitions(10)
                .replicas(3)
                .compact()
                .build();
    }
}
