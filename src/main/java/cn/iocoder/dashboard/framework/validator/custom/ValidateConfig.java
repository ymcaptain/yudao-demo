package cn.iocoder.dashboard.framework.validator.custom;

import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.validation.Validator;

/**
 * 参数校验器配置类
 *
 * @author zzf
 * @date 2021/4/13 14:13
 */
@Configuration(proxyBeanMethods = false)
public class ValidateConfig {

    /**
     * 配置一个校验器的代理类，自定义选择校验器进行校验
     * <p>
     * 这里必须加上@Primary注解，否则会跟{@link WebMvcConfigurationSupport#mvcValidator()}的冲突
     */
    @Primary
    @Bean
    public Validator primaryValidator() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory();
        factoryBean.setMessageInterpolator(interpolatorFactory.getObject());
        CustomValidator customValidator = new CustomValidator();
        factoryBean.afterPropertiesSet();
        return new ValidatorProxy(factoryBean.getValidator(), customValidator);
    }

}
