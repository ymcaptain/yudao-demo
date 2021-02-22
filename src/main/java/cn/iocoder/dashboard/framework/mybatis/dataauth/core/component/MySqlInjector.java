package cn.iocoder.dashboard.framework.mybatis.dataauth.core.component;

import cn.iocoder.dashboard.framework.mybatis.dataauth.core.annotion.DataAuth;
import cn.iocoder.dashboard.framework.mybatis.dataauth.core.annotion.NotDataAuth;
import cn.iocoder.dashboard.framework.mybatis.dataauth.core.entity.DataAuthCache;
import com.baomidou.mybatisplus.core.MybatisMapperAnnotationBuilder;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zzf
 * @date 2021/2/5 15:02
 */
@Slf4j
public class MySqlInjector extends DefaultSqlInjector {

    @Override
    public void inspectInject(MapperBuilderAssistant builderAssistant, Class<?> mapperClass) {
        String resource = getResource(mapperClass);


        Set<String> needAuthMethod = new HashSet<>();
        Set<String> notNeedAuthMethod = new HashSet<>();

        if (mapperClass.getAnnotation(DataAuth.class) != null) {
            DataAuthCache.needAuthResource.add(resource);

            for (Method method : mapperClass.getMethods()) {
                needAuthMethod.add(method.getName());
            }
        }


        for (Method method : mapperClass.getMethods()) {
            if (method.getAnnotation(DataAuth.class) != null) {
                needAuthMethod.add(method.getName());
            }
            if (method.getAnnotation(NotDataAuth.class) != null) {
                notNeedAuthMethod.add(method.getName());
            }
        }
        if (needAuthMethod.size() > 0) {
            DataAuthCache.needAuthMethodMap.put(resource, needAuthMethod);
            DataAuthCache.resource2EntityClassMap.put(resource, extractModelClass(mapperClass));
        }
        if (notNeedAuthMethod.size() > 0) {
            DataAuthCache.notNeedAuthMethodMap.put(resource, notNeedAuthMethod);
        }
        super.inspectInject(builderAssistant, mapperClass);
    }

    /**
     * copy from{@link MapperAnnotationBuilder} at line 109 , {@link MybatisMapperAnnotationBuilder} at line 84
     */
    private String getResource(Class<?> mapperClass) {
        return mapperClass.getName().replace('.', '/') + ".java (best guess)";
    }
}
