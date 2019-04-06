package com.ob.base.repository;

import com.ob.base.domain.BaseDomain;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * @author: oubin
 * @date: 2019/4/3 15:22
 * @Description:
 */
@NoRepositoryBean
public interface CustomRepository<T extends BaseDomain<I>, I extends Serializable> extends PagingAndSortingRepository<T, I> {

    void doSomething(I id);
}
