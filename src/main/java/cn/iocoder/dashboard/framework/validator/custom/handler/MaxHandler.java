package cn.iocoder.dashboard.framework.validator.custom.handler;

import org.slf4j.helpers.MessageFormatter;

import javax.validation.constraints.Max;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 最大值校验处理类
 *
 * @author zzf
 * @date 2021/4/15 9:21
 */
public class MaxHandler implements ValidateAnnotationHandler<Max> {

    @Override
    public Class<Max> getAnnotation() {
        return Max.class;
    }

    @Override
    public String validate(Max validateAnnotation, Object fieldValue) {
        long value = validateAnnotation.value();
        boolean valid;
        if (fieldValue instanceof Integer) {
            valid = value > (Integer) fieldValue;
        } else if (fieldValue instanceof BigDecimal | fieldValue instanceof Double | fieldValue instanceof Short) {
            valid = new BigDecimal(String.valueOf(value)).compareTo(new BigDecimal(String.valueOf(fieldValue))) > 0;
        } else if (fieldValue instanceof Long) {
            valid = value > (Long) fieldValue;
        } else if (fieldValue instanceof Date) {
            valid = value > ((Date) fieldValue).getTime();
        } else {
            valid = true;
        }
        if (!valid) {
            MessageFormatter.format(getResultMsgWhenInvalid(), value).getMessage();
        }
        return null;
    }

    @Override
    public String getResultMsgWhenInvalid() {
        return "的值必须小于{}!";
    }
}
