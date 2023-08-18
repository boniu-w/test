package wg.application.exception;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class Assert {
    public Assert() {
    }

    public static void isTrue(boolean expression, String message, Object... params) {
        if (!expression) {
            throw new TheException(message);
        }
    }

    public static void isFalse(boolean expression, String message, Object... params) {
        isTrue(!expression, message, params);
    }

    public static void isNull(Object object, String message, Object... params) {
        if (object == null) {
            throw new TheException(message);
        }
    }

    public static void notNull(Object object, String message, Object... params) {
        isTrue(object != null, message, params);
    }

    public static void notEmpty(String value, String message, Object... params) {
        isTrue(StringUtils.isNotBlank(value), message, params);
    }

    public static void notEmpty(Collection<?> collection, String message, Object... params) {
        isTrue(CollectionUtils.isNotEmpty(collection), message, params);
    }

    // public static void notEmpty(Map<?, ?> map, String message, Object... params) {
    //     isTrue(CollectionUtils.isNotEmpty(map), message, params);
    // }

    public static void notEmpty(Object[] array, String message, Object... params) {
        isTrue(ArrayUtils.isNotEmpty(array), message, params);
    }

    public static void isArrayEmpty(Object[] array, String... params) {
        isArrayEmpty(array, ErrorCode.NOT_NULL, params);
    }

    public static void isArrayEmpty(Object[] array, Integer code, String... params) {
        if (code == null) {
            throw new TheException(ErrorCode.NOT_NULL, "code");
        }

        if (ArrayUtil.isEmpty(array)) {
            throw new TheException(code, params);
        }
    }

    public static void isListEmpty(List<?> list, String... params) {
        isListEmpty(list, ErrorCode.NOT_NULL, params);
    }

    public static void isListEmpty(List<?> list, Integer code, String... params) {
        if (code == null) {
            throw new TheException(ErrorCode.NOT_NULL, "code");
        }

        if (CollUtil.isEmpty(list)) {
            throw new TheException(code, params);
        }
    }

    public static void isMapEmpty(Map map, String... params) {
        isMapEmpty(map, ErrorCode.NOT_NULL, params);
    }

    public static void isMapEmpty(Map map, Integer code, String... params) {
        if (code == null) {
            throw new TheException(ErrorCode.NOT_NULL, "code");
        }

        if (MapUtil.isEmpty(map)) {
            throw new TheException(code, params);
        }
    }

    public static void isFalse(boolean bool, String message) {
        if (!bool) {
            throw new TheException(message);
        }
    }
}