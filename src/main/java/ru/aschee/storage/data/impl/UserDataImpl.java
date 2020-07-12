package ru.aschee.storage.data.impl;

import com.querydsl.core.group.Group;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.aschee.domain.User;
import ru.aschee.exception.NotFoundException;
import ru.aschee.model.UserInfo;
import ru.aschee.model.UserSortField;
import ru.aschee.model.request.ListUserRequest;
import ru.aschee.storage.Storage;
import ru.aschee.storage.data.UserData;
import ru.aschee.storage.repository.UserRepository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Function;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static ru.aschee.domain.QCompany.company;
import static ru.aschee.domain.QContact.contact;
import static ru.aschee.domain.QUser.user;

@Primary
@Component
public class UserDataImpl extends Storage implements UserData {

    private final UserRepository repository;

    public UserDataImpl(UserRepository repository) {
        super(User.class);
        this.repository = repository;
    }

    private Predicate filter(String name) {
        Function<String, Predicate> function = value -> user.nickname.equalsIgnoreCase(value)
                .or(user.firstName.containsIgnoreCase(value))
                .or(user.lastName.containsIgnoreCase(value));
        return safe(function, name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findUsersByRequest(@Nonnull ListUserRequest request) {
        return from(user)
                .where(filter(request.getName()))
                .orderBy(orderSpecifier(request.getSortField(), request.getSortOrder()),
                        user.id.asc())
                .fetch();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfo findUserInfoById(String id) {
        List<Group> groups = from(user)
                .select(user, company, contact)
                .leftJoin(company).on(company.userId.eq(id))
                .leftJoin(contact).on(contact.userId.eq(id)).fetchJoin()
                .where(user.id.eq(id))
                .transform(groupBy(user).list(company, list(contact)));

        return groups.stream()
                .findFirst()
                .map(group -> UserInfo.builder()
                        .user(group.getOne(user))
                        .company(group.getOne(company))
                        .contacts(group.getList(contact))
                        .build())
                .orElseThrow(() -> new NotFoundException("User not found, id: " + id));
    }

    @Override
    @Transactional
    public User putUser(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean nicknameIsExists(String nickname) {
        return repository.existsByNickname(nickname);
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        repository.deleteById(id);
    }

    private OrderSpecifier<?> orderSpecifier(UserSortField sortField, Order order) {
        ComparableExpressionBase<?> expressionBase = orderByField(sortField);
        return orderSpecifier(expressionBase, order);
    }

    private ComparableExpressionBase<?> orderByField(UserSortField sortField) {
        return switch (sortField) {
            case nickname -> user.nickname;
            case firstName -> user.firstName;
            case lastName -> user.lastName;
        };
    }

    private OrderSpecifier<?> orderSpecifier(ComparableExpressionBase<?> expressionBase, Order order) {
        return Order.ASC == order ? expressionBase.asc() : expressionBase.desc();
    }
}
