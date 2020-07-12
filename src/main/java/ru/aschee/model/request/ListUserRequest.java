package ru.aschee.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import ru.aschee.model.UserSortField;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class ListUserRequest extends ListRequest {
    private String name;
    private UserSortField sortField;
}
