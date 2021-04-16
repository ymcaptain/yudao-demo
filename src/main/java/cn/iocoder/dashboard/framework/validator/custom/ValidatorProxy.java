package cn.iocoder.dashboard.framework.validator.custom;

import io.swagger.annotations.ApiModelProperty;
import lombok.SneakyThrows;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 自定义参数校验器
 * <p>
 * ###未完成###
 * <p>
 * 愿景：实现自动生成验证失败的消息
 * 1、字段名根据{@link ApiModelProperty#value()}注解获取
 * 2、状态根据{@link NotNull}、{@link javax.validation.constraints.NotEmpty}等生成
 * 3、拼接返回消息
 * <p>
 * 如果实现后则以后只需要加{@code @NotNull @NotEmpty}注解，不需要再一个个写{@link NotNull#message()}的值
 *
 * @author zzf
 * @date 2021/4/8 14:09
 */
public class ValidatorProxy implements Validator, ExecutableValidator {

    private final Validator defaultValidator;

    private final Validator customValidator;

    public ValidatorProxy(Validator defaultValidator,
                          Validator customValidator) {
        this.defaultValidator = defaultValidator;
        this.customValidator = customValidator;
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
        return customValidator.validate(object, groups);
    }

    @SneakyThrows
    @Override
    public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
        return defaultValidator.validateProperty(object, propertyName, groups);
    }

    @SneakyThrows
    @Override
    public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
        return defaultValidator.validateValue(beanType, propertyName, value, groups);
    }

    @Override
    public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
        return defaultValidator.getConstraintsForClass(clazz);
    }

    @SneakyThrows
    @Override
    public <T> T unwrap(Class<T> type) {
        return type.newInstance();
    }

    @Override
    public ExecutableValidator forExecutables() {
        return this;
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateParameters(T object, Method method, Object[] parameterValues, Class<?>... groups) {
        return customValidator.forExecutables().validateParameters(object, method, parameterValues, groups);
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateReturnValue(T object, Method method, Object returnValue, Class<?>... groups) {
        return defaultValidator.forExecutables().validateReturnValue(object, method, returnValue, groups);
    }

    @SneakyThrows
    @Override
    public <T> Set<ConstraintViolation<T>> validateConstructorParameters(Constructor<? extends T> constructor, Object[] parameterValues, Class<?>... groups) {
        return defaultValidator.forExecutables().validateConstructorParameters(constructor, parameterValues, groups);
    }

    @SneakyThrows
    @Override
    public <T> Set<ConstraintViolation<T>> validateConstructorReturnValue(Constructor<? extends T> constructor, T createdObject, Class<?>... groups) {
        return defaultValidator.forExecutables().validateConstructorReturnValue(constructor, createdObject, groups);
    }
}
