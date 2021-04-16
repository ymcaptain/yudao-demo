package cn.iocoder.dashboard.framework.validator.custom;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;

import javax.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 校验注解枚举
 *
 * @author zzf
 * @date 2021/4/13 11:35
 */
@Slf4j
@Getter
@AllArgsConstructor
public enum ValidateAnnotationEnum {

    NOT_NULL(NotNull.class,
            "必传！",
            (object, param) -> {
                return ObjectUtil.isNotNull(object);
            }
    ),

    NOT_EMPTY(NotEmpty.class,
            "内容不能为空！",
            (object, param) -> {
                return ObjectUtil.isNotEmpty(object);
            }
    ),

    NOT_BLANK(NotBlank.class,
            "内容需包含有效字符！",
            (object, param) -> {
                if (object instanceof String) {
                    return StrUtil.isNotBlank(String.valueOf(object));
                }
                return false;
            }
    ),

    MAX(true,
            Max.class,
            "不能超过{}",
            (object, param) -> {
                if (object instanceof Integer) {
                    return param > (Integer) object;
                }
                if (object instanceof BigDecimal | object instanceof Double | object instanceof Short) {
                    return new BigDecimal(String.valueOf(param)).compareTo(new BigDecimal(String.valueOf(object))) > 0;
                }
                if (object instanceof Long) {
                    return param > (Long) object;
                }
                if (object instanceof Date) {
                    return param > ((Date) object).getTime();
                }
                return false;
            }
    ),

    MIN(true,
            Min.class,
            "不能小于{}",
            (object, param) -> {
                if (object instanceof Integer) {
                    return param < (Integer) object;
                }
                if (object instanceof BigDecimal | object instanceof Double | object instanceof Short) {
                    return new BigDecimal(String.valueOf(param)).compareTo(new BigDecimal(String.valueOf(object))) < 0;
                }
                if (object instanceof Long) {
                    return param < (Long) object;
                }
                if (object instanceof Date) {
                    return param < ((Date) object).getTime();
                }
                return false;
            });

    private final boolean hadParam;

    private final Class<? extends Annotation> annotation;

    private final String msg;

    private final BiFunction<Object, Long, Boolean> checkFunction;

    ValidateAnnotationEnum(Class<? extends Annotation> annotation, String msg, BiFunction<Object, Long, Boolean> checkFunction) {
        this.hadParam = false;
        this.annotation = annotation;
        this.msg = msg;
        this.checkFunction = checkFunction;
    }

    public String formatMsg(String fieldName, String... param) {
        if (hadParam) {
            return fieldName + MessageFormatter.arrayFormat(msg, param).getMessage();
        }
        return fieldName + msg;
    }

    public boolean checkParam(Field field, Object object, Long... param) throws IllegalAccessException {
        if (hadParam && param != null && param.length == 1) {
            return checkFunction.apply(field.get(object), param[0]);
        }
        if (!hadParam) {
            return checkFunction.apply(field.get(object), null);
        }
        log.error("[参数校验失败！]， Enum = {}, TargetClass = {}, FieldName = {}, param = {}",
                this.toString(),
                object.getClass().getSimpleName(),
                field.getName(),
                param);
        return true;
    }


    public interface IfmFunction {

        Boolean apply(Field field, Object object, Function<Object, Boolean> isValid);

    }


}
