package cn.iocoder.dashboard.framework.validator.custom.handler;

import cn.hutool.core.util.StrUtil;

import javax.validation.constraints.NotBlank;

/**
 * 非空字符校验处理类
 *
 * @author zzf
 * @date 2021/4/15 9:21
 */
public class NotBlankHandler implements ValidateAnnotationHandler<NotBlank> {

    @Override
    public Class<NotBlank> getAnnotation() {
        return NotBlank.class;
    }

    @Override
    public String validate(NotBlank validateAnnotation, Object fieldValue) {
        if(StrUtil.isBlankIfStr(fieldValue)) {
            return getResultMsgWhenInvalid();
        }
        return null;
    }

    @Override
    public String getResultMsgWhenInvalid() {
        return "格式错误或者没有有效字符!";
    }
}
