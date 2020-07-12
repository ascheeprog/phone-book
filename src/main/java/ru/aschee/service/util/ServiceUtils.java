package ru.aschee.service.util;

import com.querydsl.core.types.Order;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class ServiceUtils {
    public String generateId() {
        return UUID.randomUUID().toString();
    }

    public Order sortOrder(String order) {
        return Order.ASC.name().equalsIgnoreCase(order) ? Order.ASC : Order.DESC;
    }
}
