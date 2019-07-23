package com.ob.common.util;

import com.ob.common.exception.BizException;
import com.ob.common.exception.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: oubin
 * @date: 2019/3/27 15:41
 * @Description:
 */
public final class ValidatorUtil {

    private static final String VALIDATOR_BEAN_NAME = "validator";

    private volatile static Validator validator;

    public static <T> void throwIfInvalid(T t, Class<?>... groups) {
        String errorMessage = validate(t, groups);
        if (StringUtils.isNotEmpty(errorMessage)) {
            throw new BizException(ErrorCode.BAD_REQUEST, errorMessage);
        }
    }

    public static <T> String validate(T t, Class<?>... groups) {
        Set<ConstraintViolation<T>> constraintViolations = getValidator().validate(t, groups);
        return constraintViolations.stream()
                .map(e -> StringUtils.defaultString(e.getMessage()))
                .collect(Collectors.joining(","));
    }

    private static Validator getValidator() {
        if (null == validator) {
            synchronized (ValidatorUtil.class) {
                if (null == validator) {
                    ApplicationContext applicationContext = ApplicationContextUtil.getApplicationContext();
                    if (applicationContext != null && applicationContext.containsBean(VALIDATOR_BEAN_NAME)) {
                        validator = applicationContext.getBean(VALIDATOR_BEAN_NAME, ValidatorFactory.class).getValidator();
                    }else {
                        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                        validator = factory.getValidator();
                    }
                }
            }
        }
        return validator;
    }
}
