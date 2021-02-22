package cn.iocoder.dashboard.framework.mybatis.dataauth.core.component;


import net.sf.jsqlparser.statement.select.Select;

/**
 * 数据权限限制的处理策略
 * 比如
 * 先通过岗位限制，如果岗位不合适，再通过角色限制，再通过其他字段限制等等
 *
 * 每种限制方式应该有对应的{@link AbstractDataAuthSqlHandler}
 *
 * @author zzf
 * @date 17:54 2021/1/29
 */
public interface DataAuthStrategy {

    void doParse(Select selectStatement, Class<?> entityClass);

}