package cn.iocoder.dashboard.framework.dataauth.impl;


import cn.iocoder.dashboard.framework.dataauth.core.component.AbstractDataAuthSqlHandler;
import cn.iocoder.dashboard.framework.dataauth.core.component.DataAuthStrategy;
import cn.iocoder.dashboard.framework.dataauth.core.entity.DataAuthCache;
import net.sf.jsqlparser.statement.select.Select;

/**
 * 自定义数据权限sql处理策略类
 *
 * @author zzf
 * @date 17:54 2021/1/29
 */
public class MyDataAuthStrategy implements DataAuthStrategy {

    @Override
    public void doParse(Select selectStatement, Class<?> entityClass) {
        AbstractDataAuthSqlHandler<?> dataAuthSqlHandler = DataAuthCache.getSqlHandlerById(SqlHandlerIdEnum.ROLE.getId());
        dataAuthSqlHandler.doParse(selectStatement, entityClass);
    }

}