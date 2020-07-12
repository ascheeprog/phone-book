package ru.aschee.storage;

import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Objects;
import java.util.function.Function;

public abstract class Storage extends QuerydslRepositorySupport {
    public Storage(Class<?> domainClass) {
        super(domainClass);
    }

    protected <T> Predicate safe(Function<T, ? extends Predicate> check, T value) {
        return Objects.nonNull(value) ? check.apply(value) : null;
    }
}
