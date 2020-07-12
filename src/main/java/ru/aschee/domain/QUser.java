package ru.aschee.domain;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

@Generated("com.querysdl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 44565433456L;

    public static final QUser user = new QUser("user");

    public final StringPath id = createString("id");

    public final StringPath firstName = createString("firstName");

    public final StringPath lastName = createString("lastName");

    public final StringPath nickname = createString("nickname");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

}
