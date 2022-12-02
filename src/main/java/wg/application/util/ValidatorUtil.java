package wg.application.util;

import cn.hutool.core.lang.PatternPool;
import wg.application.exception.WgException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;
import java.util.regex.Pattern;

/************************************************************************
 * @author: wg
 * @description: 验证工具类
 * @createTime: 16:04 2022/8/22
 * @updateTime: 16:04 2022/8/22
 ************************************************************************/
public class ValidatorUtil {
    private static javax.validation.Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static final Pattern IPV4 = PatternPool.IPV4;

    public static boolean isIpv4(CharSequence value) {
        return isMatchRegex(IPV4, value);
    }

    public static boolean isMatchRegex(Pattern pattern, CharSequence value) {
        return RegexUtil.isMatch(pattern, value);
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws WgException 校验不通过，则报 WgException 异常
     */
    public static void validateEntity(Object object, Class<?>... groups) throws WgException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for (ConstraintViolation<Object> constraint : constraintViolations) {
                msg.append(constraint.getMessage()).append("<br>");
            }
            throw new WgException(msg.toString());
        }
    }

}
