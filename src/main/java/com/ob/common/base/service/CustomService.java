package com.ob.common.base.service;

import com.ob.common.base.domain.BaseDomain;
import com.ob.common.base.repository.CustomRepository;
import com.ob.common.exception.BizException;
import com.ob.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @author: oubin
 * @date: 2019/4/3 15:52
 * @Description:
 */
public class CustomService<T extends BaseDomain<I>, I extends Serializable> {

    @Autowired
    private CustomRepository<T, I> customRepository;

    /**
     * 严格获取
     *
     * @param id
     * @return
     */
    protected T strictFind(I id) {
        T t = findOne(id);
        if (null == t) {
            throw new BizException(ErrorCode.DATA_NOT_FOUND);
        }
        return t;
    }

    private T findOne(I id) {
        return customRepository.findOne(id);
    }
}
