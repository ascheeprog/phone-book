//package ru.aschee.metric;
//
//import io.micrometer.core.instrument.MeterRegistry;
//import org.springframework.stereotype.Component;
//import ru.aschee.storage.JpaClient;
//
//@Component
//public class CustomMetrics {
//    private final MeterRegistry meterRegistry;
//    private final JpaClient client;
//
//    public CustomMetrics(MeterRegistry meterRegistry, JpaClient client) {
//        this.meterRegistry = meterRegistry;
//        this.client = client;
//        totalEntitiesCount();
//    }
//
//    private void totalEntitiesCount() {
//        var totalEntityCount = client.contactsCount() + client.usersCount();
//        meterRegistry.gauge("TotalEntitiesCount", totalEntityCount);
//    }
//}
