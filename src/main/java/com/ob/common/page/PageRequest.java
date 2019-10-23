package com.ob.common.page;

import com.google.common.collect.Lists;
import com.ob.common.constant.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

/**
 * @Author: oubin
 * @Date: 2019/10/22 15:04
 * @Description:
 */
@Setter
@Getter
public class PageRequest {
    private Integer offset;
    private Integer limit;
    private Sort sort;
    /**
     * 是否统计  count
     */
    private Boolean count;
    /**
     * 是否分页
     */
    private Boolean pageable;

    /**
     * 是否模糊匹配
     */
    private FuzzyMatching fuzzyMatching;

    public Integer getOffset() {
        if (offset == null || offset < 0) {
            return Constants.DEFAULT_OFFSET;
        }
        return offset;
    }

    public Integer getLimit() {
        if (!getPageable()) {
            return 0;
        }
        if (limit == null || limit <= 0) {
            return Constants.DEFAULT_LIMIT;
        }
        return Math.min(limit, Constants.DEFAULT_MAX_LIMIT);
    }

    /**
     * 返回根据所在页与每页数量计算出的偏移量。
     *
     * @return 偏移量
     */
    public int getSize() {
        return limit * offset;
    }

    public PageRequest addSort(Sort sort) {
        if (null == this.sort) {
            this.sort = sort;
        } else {
            this.sort = this.sort.and(sort);
        }
        return this;
    }

    public PageRequest addFuzzy(FuzzyMatching.Fuzzy fuzzy) {
        if (null == this.fuzzyMatching) {
            this.setFuzzyMatching(new FuzzyMatching(Lists.newArrayList(fuzzy)));
        } else {
            this.fuzzyMatching.and(fuzzy);
        }
        return this;
    }
}
