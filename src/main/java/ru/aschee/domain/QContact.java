package ru.aschee.domain;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

@Generated("com.querysdl.codegen.EntitySerializer")
public class QContact extends EntityPathBase<Contact> {

    private static final long serialVersionUID = 123453401L;

    public static final QContact contact = new QContact("contact");

    public final StringPath id = createString("id");

    public final StringPath firstName = createString("firstName");

    public final StringPath lastName = createString("lastName");

    public final StringPath phone = createString("phone");

    public final StringPath email = createString("email");

    public final StringPath url = createString("url");

    public final StringPath userId = createString("userId");

    public QContact(String variable) {
        super(Contact.class, forVariable(variable));
    }

    public QContact(PathMetadata metadata) {
        super(Contact.class, metadata);
    }

    public QContact(Path<? extends Contact> path) {
        super(path.getType(), path.getMetadata());
    }
}
