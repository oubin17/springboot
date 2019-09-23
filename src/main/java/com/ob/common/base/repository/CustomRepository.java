package com.ob.common.base.repository;

import com.ob.common.base.domain.BaseDomain;
import com.ob.common.exception.BizException;
import com.ob.common.exception.ErrorCode;
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

    /**
     * 提供默认方法，严格获取对象
     *
     * @param id
     * @return
     */
    default T strictFind(I id) {
        T t = this.findOne(id);
        if (null == t) {
            throw new BizException(ErrorCode.DATA_NOT_FOUND);
        }
        return t;
    }
}
