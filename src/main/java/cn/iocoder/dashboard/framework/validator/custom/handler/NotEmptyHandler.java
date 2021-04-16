package cn.iocoder.dashboard.framework.validator.custom.handler;

import cn.hutool.core.util.ObjectUtil;

import javax.validation.constraints.NotEmpty;

/**
 * 内容不可空校验处理类
 *
 * @author zzf
 * @date 2021/4/15 9:21
 */
public class NotEmptyHandler implements ValidateAnnotationHandler<NotEmpty> {

    @Override
    public Class<NotEmpty> getAnnotation() {
        return NotEmpty.class;
    }

    @Override
    public String validate(NotEmpty validateAnnotation, Object fieldValue) {
        if(ObjectUtil.isEmpty(fieldValue)) {
            return getResultMsgWhenInvalid();
        }
        return null;
    }

    @Override
    public String getResultMsgWhenInvalid() {
        return "不能为空!!!!";
    }
}
