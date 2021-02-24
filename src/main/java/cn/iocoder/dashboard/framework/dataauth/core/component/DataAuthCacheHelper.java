package cn.iocoder.dashboard.framework.dataauth.core.component;

import cn.iocoder.dashboard.framework.dataauth.core.entity.DataAuthCache;
import cn.iocoder.dashboard.framework.dataauth.core.entity.DataAuthConstants;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.http.util.Asserts;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.util.*;

/**
 * 添加数据权限限制配置的辅助工具
 *
 * @author zzf
 * @date 2021/2/4 15:35
 */
@Accessors(chain = true)
public class DataAuthCacheHelper {

    /**
     * 该表对应的实体类
     */
    @Setter
    private Set<Class<?>> targetEntityClassSet;

    /**
     * 数据权限限制处理器
     */
    @Setter
    private AbstractDataAuthSqlHandler<?> sqlHandler;

    /**
     * 对象表 用于与数据权限限制的表关联 的字段名
     */
    private String targetFieldName;

    /**
     * 保存上面三个参数的对应关系map
     * sqlHandlerId : targetClass : fieldName map
     * <p>
     * 用于在处理SQL时，根据对应关系修改SQL
     */
    private final Map<AbstractDataAuthSqlHandler<?>, Map<Class<?>, String>> sqlHandler2TargetClass2FieldNameMap = new HashMap<>();


    public DataAuthCacheHelper addTargetEntityClass(Class<?> targetEntityClass) {
        if (this.targetEntityClassSet == null) {
            this.targetEntityClassSet = new HashSet<>();
        }
        this.targetEntityClassSet.add(targetEntityClass);
        return this;
    }

    public DataAuthCacheHelper addTargetEntityClass(Collection<Class<?>> targetEntityClassList) {
        if (this.targetEntityClassSet == null) {
            this.targetEntityClassSet = new HashSet<>();
        }
        this.targetEntityClassSet.addAll(targetEntityClassList);
        return this;
    }

    public <T> DataAuthCacheHelper setTargetFieldName(SFunction<T, ?> function) {
        targetFieldName = PropertyNamer.methodToProperty(LambdaUtils.resolve(function).getImplMethodName());
        return this;
    }

    /**
     * 绑定前三个参数的信息，生成 sqlHandler2TargetClass2FieldNameMap 数据
     */
    public DataAuthCacheHelper bind() {
        valueCheck();
        Map<Class<?>, String> targetClass2FieldNameMap = new HashMap<>();
        targetEntityClassSet.forEach(entityClass -> {
            //直接从MP的缓存中获取实体类对应的表信息，查询实体类字段对应的表字段名
            TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
            //TableInfo中 主键和其他字段分别存储，因此这里要分别判断。
            if (targetFieldName.equals(tableInfo.getKeyProperty())) {
                targetClass2FieldNameMap.put(entityClass, tableInfo.getKeyColumn());
            } else {
                Optional<String> relationColumnOptional = tableInfo.getFieldList().stream()
                        .filter(s -> s.getProperty().equals(targetFieldName))
                        .map(TableFieldInfo::getColumn)
                        .findFirst();
                if (relationColumnOptional.isPresent()) {
                    targetClass2FieldNameMap.put(entityClass, relationColumnOptional.get());
                } else {
                    throw new IllegalArgumentException(DataAuthConstants.notExistsFieldInfo(targetFieldName));
                }
            }
        });
        sqlHandler2TargetClass2FieldNameMap.put(sqlHandler, targetClass2FieldNameMap);
        reset();
        return this;
    }

    /**
     * 前三个参数数据验证
     */
    public void valueCheck() {
        Asserts.notNull(this.targetEntityClassSet, "targetEntityClassList can not be null");
        Asserts.notNull(this.sqlHandler, "sqlHandlerList can not be null");
        Asserts.notNull(this.targetFieldName, "targetFieldName can not be null");
    }

    /**
     * 重置前三个参数的数据
     */
    public void reset() {
        targetEntityClassSet = null;
        sqlHandler = null;
        targetFieldName = null;
    }

    /**
     * 加入到缓存中
     */
    public void cache() {
        sqlHandler2TargetClass2FieldNameMap.forEach((key, val) -> {
            DataAuthCache.addSqlHandler(key);
            DataAuthCache.addSqlHandlerId2TargetClass2FieldNameMap(key.getId(), val);
        });
    }

}
