package cn.iocoder.dashboard.framework.mybatis.dataauth.core.entity;

import cn.iocoder.dashboard.framework.mybatis.dataauth.core.component.AbstractDataAuthSqlHandler;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** TODO FROM 芋艿 to zzf：最好不要暴露原始的数据结构出去
 * @author zzf
 * @date 2021/2/5 15:02
 */
public interface DataAuthCache {

    /**
     * 需要权限限制的resource集合 from {@link MappedStatement#getResource()}
     */
    Set<String> needAuthResource = new HashSet<>();

    /**
     * 需要权限限制的 resource:方法名集合 map
     */
    Map<String, Set<String>> needAuthMethodMap = new HashMap<>();

    /**
     * 不需要权限限制的 resource:方法名集合 的 map
     */
    Map<String, Set<String>> notNeedAuthMethodMap = new HashMap<>(32);

    /**
     * resource:对应表实体类Class对象 map
     */
    Map<String, Class<?>> resource2EntityClassMap = new HashMap<>(32);

    Map<Integer, AbstractDataAuthSqlHandler<?>> sqlHandlerMap = new HashMap<>(8);

    Map<Integer, Map<Class<?>, String>> sqlHandlerId2TargetClass2FieldNameMap = new HashMap<>(8);

    static String getFieldName(Integer sqlHandlerId, Class<?> entityClass) {
        Map<Class<?>, String> classStringMap = sqlHandlerId2TargetClass2FieldNameMap.get(sqlHandlerId);
        return classStringMap.get(entityClass);
    }

}
