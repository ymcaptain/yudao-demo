package cn.iocoder.dashboard.framework.mybatis.dataauth.core.component;

import cn.hutool.core.util.ReflectUtil;
import cn.iocoder.dashboard.framework.mybatis.dataauth.core.entity.DataAuthCache;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

/**
 * 数据权限拦截器
 *
 * @author zzf
 * @date 11:05 2021/2/4
 */
@Slf4j
public class DataAuthInterceptor implements InnerInterceptor {

    //限制策略
    private final DataAuthStrategy dataAuthStrategy;

    private final Set<String> needAuthId = new TreeSet<>();
    private final Set<String> notNeedAuthId = new TreeSet<>();

    public DataAuthInterceptor(DataAuthStrategy dataAuthStrategy) {
        this.dataAuthStrategy = dataAuthStrategy;
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        String id = ms.getId();

        String resource = ms.getResource();

        String methodName;
        if (id.contains(".")) {
            methodName = id.substring(id.lastIndexOf(".") + 1);
        } else {
            methodName = id;
        }
        if (needAuthCheck(resource, methodName, id)) {
            log.debug("sql before parsed: " + boundSql.getSql());
            Class<?> entityClass = DataAuthCache.resource2EntityClassMap.get(resource);
            try {
                Select selectStatement = (Select) CCJSqlParserUtil.parse(boundSql.getSql());
                dataAuthStrategy.doParse(selectStatement, entityClass);

                ReflectUtil.setFieldValue(boundSql, "sql", selectStatement.toString());
                log.debug("sql after parsed: " + boundSql.getSql());

            } catch (JSQLParserException e) {
                throw new SQLException("sql role limit fail: sql parse fail.");
            }

        }
    }


    /**
     * 判断是否需要数据权限限制
     */
    private boolean needAuthCheck(String resource, String methodName, String id) {
        if (needAuthId.contains(id)) {
            return true;
        }
        if (notNeedAuthId.contains(id)) {
            return false;
        }
        boolean result;
        if (DataAuthCache.needAuthResource.contains(resource)) {
            Set<String> noNeedAuthMethod = DataAuthCache.notNeedAuthMethodMap.get(resource);
            result = ObjectUtils.isEmpty(noNeedAuthMethod) || !noNeedAuthMethod.contains(methodName);
        } else {
            Set<String> needAuthMethod = DataAuthCache.needAuthMethodMap.get(resource);
            result = ObjectUtils.isNotEmpty(needAuthMethod) && !needAuthMethod.contains(methodName);
        }

        if (result) {
            needAuthId.add(id);
        } else {
            notNeedAuthId.add(id);
        }
        return result;
    }

}