package cn.iocoder.dashboard.framework.dataauth.core.util;

import com.baomidou.mybatisplus.core.MybatisMapperAnnotationBuilder;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;

/**
 * mybatis mapper resource相关工具
 * <p>
 * 在mybatis中，每个mapper方法对应一个字符串类型的resource
 * resource的值有两种生成方式
 * 1、当该方法没有对应的xml实现时：{@link MapperAnnotationBuilder} at line 109 , {@link MybatisMapperAnnotationBuilder} at line 84
 * 2、当该方法有对应的xml实现时：{@link MapperAnnotationBuilder} at line 166, {@link MybatisMapperAnnotationBuilder} at line 161
 * 这两种我们都需要拦截，为了方便后续操作，我们直接统一处理
 *
 * @author zzf
 * @date 2021/2/26 11:40
 */
public class ResourceUtils {

    private static final String MAPPER_SUFFIX = ".java (best guess)";

    private static final String XML_SUFFIX = ".xml";

    /**
     * 根据mybatis提供的resource 获取 DataAuth过程中需要的resourceKey
     *
     * @param resource 根据mybatis提供的resource
     * @return DataAuth过程中需要的resourceKey
     * @see cn.iocoder.dashboard.framework.dataauth.core.component.DataAuthInterceptor
     */
    public static String getResourceKey(String resource) {
        String fileSeparator = System.getProperties().getProperty("file.separator");
        resource = resource.replace("\\", fileSeparator);
        if (resource.contains(MAPPER_SUFFIX)) {
            if (resource.contains(fileSeparator)) {
                resource = resource.substring(resource.lastIndexOf(fileSeparator) + 1);
            }
            return resource.substring(0, resource.indexOf(MAPPER_SUFFIX));
        } else if (resource.contains(XML_SUFFIX)) {
            if (resource.contains(fileSeparator)) {
                resource = resource.substring(resource.lastIndexOf(fileSeparator) + 1);
            }
            return resource.substring(0, resource.indexOf(XML_SUFFIX));
        }
        throw new IllegalArgumentException("invalid resource: " + resource);
    }

    /**
     * 根据mapper类 获取 DataAuth过程中需要的resourceKey
     *
     * @param mapperClass mapper类类对象
     * @return DataAuth过程中需要的resourceKey
     * @see cn.iocoder.dashboard.framework.dataauth.core.component.DataAuthSqlInjector
     */
    public static String getResourceKey(Class<?> mapperClass) {
        return mapperClass.getSimpleName();
    }
}
