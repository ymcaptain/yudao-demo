package cn.iocoder.dashboard.framework.dataauth.core.component;

import cn.iocoder.dashboard.framework.dataauth.core.annotion.DataAuth;
import cn.iocoder.dashboard.framework.dataauth.core.annotion.DataAuthIgnore;
import cn.iocoder.dashboard.framework.dataauth.core.entity.DataAuthCache;
import com.baomidou.mybatisplus.core.MybatisMapperAnnotationBuilder;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 数据权限限制SqlInjector
 * <p>
 * 重写mp的mapper扫描逻辑
 * 在扫描mapper后检查是否需要数据权限限制并缓存这些信息
 *
 * @author zzf
 * @date 2021/2/5 15:02
 */
@Slf4j // TODO FROM 芋艿 to zzf：类名可能需要在想想哈  DONE
public class DataAuthSqlInjector extends DefaultSqlInjector {

    @Override
    public void inspectInject(MapperBuilderAssistant builderAssistant, Class<?> mapperClass) {
        String resource = getResource(mapperClass);

        // TODO FROM 芋艿 to zzf：复数  DONE
        Set<String> needAuthMethodSet = new HashSet<>();
        Set<String> notNeedAuthMethodSet = new HashSet<>();

        if (mapperClass.isAnnotationPresent(DataAuth.class)) { // TODO FROM 芋艿 to zzf：可以考虑使用 isAnnotationPresent 方法，简洁 DONE
            DataAuthCache.addNeedAuthResource(resource);

            for (Method method : mapperClass.getMethods()) {
                needAuthMethodSet.add(method.getName());
            }
        }

        for (Method method : mapperClass.getMethods()) {
            if (method.isAnnotationPresent(DataAuth.class)) {
                needAuthMethodSet.add(method.getName());
            }
            if (method.isAnnotationPresent(DataAuthIgnore.class)) {
                notNeedAuthMethodSet.add(method.getName());
            }
        }
        if (needAuthMethodSet.size() > 0) {
            DataAuthCache.addNeedAuthMethod(resource, needAuthMethodSet);
            DataAuthCache.addResource2EntityClassMap(resource, extractModelClass(mapperClass));
        }
        if (notNeedAuthMethodSet.size() > 0) {
            DataAuthCache.addNotNeedAuthMethod(resource, notNeedAuthMethodSet);
        }
        super.inspectInject(builderAssistant, mapperClass);
    }

    /**
     * copy from{@link MapperAnnotationBuilder} at line 109 , {@link MybatisMapperAnnotationBuilder} at line 84
     */
    private static String getResource(Class<?> mapperClass) {
        return mapperClass.getName().replace('.', '/') + ".java (best guess)";
    }

}
