package cn.iocoder.dashboard.framework.dataauth.core.entity;

import cn.iocoder.dashboard.framework.dataauth.core.component.AbstractDataAuthSqlHandler;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 数据权限限制的缓存信息存储类
 * TODO FROM 芋艿 to zzf：最好不要暴露原始的数据结构出去 DONE
 *
 * @author zzf
 * @date 2021/2/5 15:02
 */
public class DataAuthCache {

    /**
     * 需要权限限制的resource集合 from {@link MappedStatement#getResource()}
     */
    private final static Set<String> needAuthResourceSet = new HashSet<>();

    /**
     * 需要权限限制的 resource:方法名集合 map
     */
    private final static Map<String, Set<String>> needAuthMethodMap = new HashMap<>();

    /**
     * 不需要权限限制的 resource:方法名集合 的 map
     */
    private final static Map<String, Set<String>> notNeedAuthMethodMap = new HashMap<>(32);

    /**
     * resource:对应表实体类Class对象 map
     */
    private final static Map<String, Class<?>> resource2EntityClassMap = new HashMap<>(32);

    /**
     * 数据权限限制sql处理器
     */
    private final static Map<Integer, AbstractDataAuthSqlHandler<?>> sqlHandlerMap = new HashMap<>(8);

    /**
     * sql处理器id : 需要限制的表对应的类 : 该表用于 与数据权限限制的表做表关联 的列名
     */
    private final static Map<Integer, Map<Class<?>, String>> sqlHandlerId2TargetClass2FieldNameMap = new HashMap<>(8);

    /**
     * 添加需要权限限制的resource
     */
    public static void addNeedAuthResource(String needAuthResource) {
        needAuthResourceSet.add(needAuthResource);
    }

    /**
     * 是否是 需要权限限制的resource
     */
    public static boolean isNeedAuthResource(String resource) {
        return needAuthResourceSet.contains(resource);
    }

    /**
     * 添加需要权限限制的resource:方法名
     */
    public static void addNeedAuthMethod(String resource, Set<String> methodSet) {
        needAuthMethodMap.put(resource, methodSet);
    }

    /**
     * 是否是 需要权限限制的方法
     */
    public static boolean isNeedAuthMethod(String resource, String methodName) {
        Set<String> needAuthMethod = DataAuthCache.needAuthMethodMap.get(resource);
        return ObjectUtils.isNotEmpty(needAuthMethod) && needAuthMethod.contains(methodName);
    }

    /**
     * 添加需要权限限制的resource:方法名
     */
    public static void addNotNeedAuthMethod(String resource, Set<String> methodSet) {
        notNeedAuthMethodMap.put(resource, methodSet);
    }

    /**
     * 是否是 不需要权限限制的方法
     */
    public static boolean isNotNeedAuthMethod(String resource, String methodName) {
        Set<String> methodSet = DataAuthCache.notNeedAuthMethodMap.get(resource);
        return ObjectUtils.isNotEmpty(methodSet) && methodSet.contains(methodName);
    }

    /**
     * 添加 resource: 对应实体类类对象 关联关系
     */
    public static void addResource2EntityClassMap(String resource, Class<?> entityClass) {
        resource2EntityClassMap.put(resource, entityClass);
    }

    /**
     * 通过resource获取其对应实体类
     */
    public static Class<?> getEntityClassByResource(String resource) {
        return resource2EntityClassMap.get(resource);
    }

    /**
     * 添加数据权限限制sql处理器
     */
    public static void addSqlHandler(AbstractDataAuthSqlHandler<?> sqlHandler) {
        sqlHandlerMap.put(sqlHandler.getId(), sqlHandler);
    }

    /**
     * 添加数据权限限制sql处理器id获取对应处理器
     */
    public static AbstractDataAuthSqlHandler<?> getSqlHandlerById(Integer id) {
        return sqlHandlerMap.get(id);
    }

    /**
     * 添加数据权限限制sql处理器
     */
    public static void addSqlHandlerId2TargetClass2FieldNameMap(Integer sqlHandlerId,
                                                                Map<Class<?>, String> targetClass2RelationColumnMap) {
        sqlHandlerId2TargetClass2FieldNameMap.put(sqlHandlerId, targetClass2RelationColumnMap);
    }

    /**
     * 根据sqlHandlerId和targetEntityClass 获取 其用于表关联的列名
     *
     * @param sqlHandlerId      SqlHandler的id
     * @param targetEntityClass 需要限制的表对应的类对象
     * @return 该表用于 与限制表做表关联的字段名
     */
    public static String getFieldName(Integer sqlHandlerId, Class<?> targetEntityClass) {
        Map<Class<?>, String> classStringMap = sqlHandlerId2TargetClass2FieldNameMap.get(sqlHandlerId);
        return classStringMap.get(targetEntityClass);
    }

}
