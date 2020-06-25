package ru.example.metric;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;
import ru.example.service.JpaClient;

@Component
public class CustomMetrics {
    private final MeterRegistry meterRegistry;
    private final JpaClient client;

    public CustomMetrics(MeterRegistry meterRegistry, JpaClient client) {
        this.meterRegistry = meterRegistry;
        this.client = client;
        entitiesTotalCount();
    }

    private void entitiesTotalCount() {
        long totalEntityCount = client.contactsCount() + client.usersCount();
        meterRegistry.gauge("TotalEntitiesCount", totalEntityCount);
    }
}
