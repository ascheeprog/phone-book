package ru.aschee.model.request;

import com.querydsl.core.types.Order;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public abstract class ListRequest {
    private Order sortOrder;
}
