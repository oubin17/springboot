package com.ob.common.base.repository;

import com.ob.common.base.domain.BaseDomain;
import com.ob.common.exception.BizException;
import com.ob.common.exception.ErrorCode;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * @author: oubin
 * @date: 2019/4/3 15:27
 * @Description:
 */
public class CustomJpaRepositoryImpl<T extends BaseDomain<I>, I extends Serializable> extends SimpleJpaRepository<T, I>
        implements CustomRepository<T, I>{

    private final EntityManager entityManager;

    /**
     * Creates a new {@link SimpleJpaRepository} to manage objects of the given {@link JpaEntityInformation}.
     *
     * @param entityInformation must not be {@literal null}.
     * @param entityManager must not be {@literal null}.
     */
    public CustomJpaRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }


    @Override
    public T strictFind(I id) {
        T t = findOne(id);
        if (null == t) {
            throw new BizException(ErrorCode.DATA_NOT_FOUND);
        }
        return t;

    }
}
