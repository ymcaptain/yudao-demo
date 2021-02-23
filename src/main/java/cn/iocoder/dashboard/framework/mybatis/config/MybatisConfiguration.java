package cn.iocoder.dashboard.framework.mybatis.config;

import cn.iocoder.dashboard.framework.mybatis.dataauth.core.component.DataAuthInterceptor;
import cn.iocoder.dashboard.framework.mybatis.dataauth.core.component.MySqlInjector;
import cn.iocoder.dashboard.framework.mybatis.dataauth.impl.MyDataAuthStrategy;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBaits 配置类
 *
 * @author 芋道源码
 */
@Configuration
@MapperScan(value = "${yudao.info.base-package}", annotationClass = Mapper.class)
public class MybatisConfiguration {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 分页插件
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // 数据权限限制
        mybatisPlusInterceptor.addInnerInterceptor(new DataAuthInterceptor(new MyDataAuthStrategy()));
        return mybatisPlusInterceptor;
    }

    /**
     * 数据权限限制中 用于扫描mapper，加载需要做权限限制的数据的类/方法
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new MySqlInjector();
    }

}
