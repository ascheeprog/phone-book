package ru.aschee.domain;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

@Generated("com.querysdl.codegen.EntitySerializer")
public class QCompany extends EntityPathBase<Company> {

    private static final long serialVersionUID = 32123464451L;

    public static final QCompany company = new QCompany("company");

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final StringPath email = createString("email");

    public final StringPath phone = createString("phone");

    public final StringPath urlLogo = createString("urlLogo");

    public final StringPath userId = createString("userId");

    public final StringPath contactId = createString("contactId");

    public QCompany(String variable) {
        super(Company.class, forVariable(variable));
    }

    public QCompany(PathMetadata metadata) {
        super(Company.class, metadata);
    }

    public QCompany(Path<? extends Company> path) {
        super(path.getType(), path.getMetadata());
    }
}
