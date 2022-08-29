package viniciuslj.vote.api.services.result;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import viniciuslj.vote.api.domain.Result;

@Slf4j
@Service
public class AgendaClosingNotificator implements AgendaClosingListener{

    @Value(value = "${kafka.results.topic.name}")
    private String resultsTopicName;

    private final KafkaTemplate<String, Result> kafkaTemplate;

    public AgendaClosingNotificator(AgendaClosingMonitor agendaClosingMonitor, KafkaTemplate<String, Result> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

        agendaClosingMonitor.addListener(this);
    }

    @Override
    public void update(Result result) {
        ListenableFuture<SendResult<String, Result>> future = kafkaTemplate.send(
                resultsTopicName,
                result.getAgendaId().toString(),
                result);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, Result> r) {
                log.info("Agenda notification sent successfully (ID {})", result.getAgendaId());
            }

            @Override
            public void onFailure(Throwable e) {
                log.error("Failed to send agenda notification (ID {})", result.getAgendaId());
                log.error(e.getMessage());
            }
        });
    }
}
