package cn.iocoder.dashboard.framework.mybatis.dataauth.impl;


import cn.iocoder.dashboard.framework.mybatis.dataauth.core.component.AbstractDataAuthSqlHandler;
import cn.iocoder.dashboard.framework.mybatis.dataauth.core.component.DataAuthStrategy;
import cn.iocoder.dashboard.framework.mybatis.dataauth.core.entity.DataAuthCache;
import net.sf.jsqlparser.statement.select.Select;

/**
 * @author zzf
 * @date 17:54 2021/1/29
 */
public class MyDataAuthStrategy implements DataAuthStrategy {


    @Override
    public void doParse(Select selectStatement, Class<?> entityClass) {
        AbstractDataAuthSqlHandler<?> dataAuthSqlHandler = DataAuthCache.sqlHandlerMap.get(SqlHandlerIdEnum.ROLE.getId());
        dataAuthSqlHandler.doParse(selectStatement, entityClass);
    }

}