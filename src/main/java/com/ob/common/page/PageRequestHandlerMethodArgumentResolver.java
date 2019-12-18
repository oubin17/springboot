package com.ob.common.page;

import com.ob.common.constant.Constants;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @Author: oubin
 * @Date: 2019/10/22 16:59
 * @Description: 分页查询参数解析
 */
public class PageRequestHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String LIMIT_PARAMETER_NAME = "$limit";
    private static final String OFFSET_PARAMETER_NAME = "$offset";
    private static final String COUNT_PARAMETER_NAME = "$count";
    private static final String PAGEABLE_PARAMETER_NAME = "pageable";
    private static final String SORT_PARAMETER_NAME = "$orderby";
    private static final String LIKE_PARAMETER_NAME = "$like";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return PageRequest.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        PageRequest pageRequest = new PageRequest();

        String limit = webRequest.getParameter(LIMIT_PARAMETER_NAME);
        if (StringUtils.isNotBlank(limit)) {
            pageRequest.setLimit(NumberUtils.toInt(limit, Constants.DEFAULT_LIMIT));
        }

        String offset = webRequest.getParameter(OFFSET_PARAMETER_NAME);
        if (StringUtils.isNotBlank(offset)) {
            pageRequest.setOffset(NumberUtils.toInt(offset, Constants.DEFAULT_OFFSET));
        }

        String count = webRequest.getParameter(COUNT_PARAMETER_NAME);
        pageRequest.setCount(StringUtils.isNotBlank(count) && BooleanUtils.toBoolean(count));

        String pageable = webRequest.getParameter(PAGEABLE_PARAMETER_NAME);
        pageRequest.setPageable(!StringUtils.isNotBlank(pageable) || BooleanUtils.toBoolean(pageable));

        String sortString = StringUtils.defaultString(webRequest.getParameter(SORT_PARAMETER_NAME), "create_time desc");
        String[] partSortStrings = sortString.split(Constants.COMMA_STR);
        for (String partSortString : partSortStrings) {
            String[] partOrderStrings = partSortString.split("\\s");
            Sort.Direction direction = Sort.Direction.ASC;
            if (partOrderStrings.length == 2) {
                direction = Sort.Direction.fromString(partOrderStrings[1]);
            }
            Sort sort = new Sort(direction, partOrderStrings[0]);
            pageRequest.addSort(sort);
        }
        String like = webRequest.getParameter(LIKE_PARAMETER_NAME);
        if (StringUtils.isNotBlank(like)) {
            String[] likeStrings = like.split(Constants.COMMA_STR);
            for (String likeString : likeStrings) {
                String[] eachLikeString = likeString.split("\\s");
                if (eachLikeString.length == 2) {
                    FuzzyMatching.Fuzzy fuzzy = new FuzzyMatching.Fuzzy(eachLikeString[0], eachLikeString[1]);
                    pageRequest.addFuzzy(fuzzy);
                }
            }

        }
        return pageRequest;
    }
}