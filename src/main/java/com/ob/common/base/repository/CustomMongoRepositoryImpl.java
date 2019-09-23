package com.ob.common.base.repository;

import com.ob.common.base.domain.BaseDomain;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.io.Serializable;

/**
 * @author: oubin
 * @date: 2019/4/3 15:32
 * @Description:
 */
public class CustomMongoRepositoryImpl<T extends BaseDomain<I>, I extends Serializable> extends SimpleMongoRepository<T, I>
        implements CustomRepository<T,I> {

    private final MongoOperations mongoOperations;
    /**
     * Creates a new {@link SimpleMongoRepository} for the given {@link MongoEntityInformation} and {@link }.
     *
     * @param metadata        must not be {@literal null}.
     * @param mongoOperations must not be {@literal null}.
     */
    public CustomMongoRepositoryImpl(MongoEntityInformation<T, I> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.mongoOperations = mongoOperations;
    }

    @Override
    public T strictFind(I id) {
        return null;

    }
}
