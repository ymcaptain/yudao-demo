package cn.iocoder.dashboard.framework.mybatis.dataauth.core.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据权限sql解析后的结果
 *
 * @author zzf
 * @date 2021/2/5 8:48
 */
@Setter
@Getter
public class SqlHandlerResult {

    /**
     * 是否拥有全部权限
     */
    private Boolean hadAll;

    /**
     * 是否没有权限
     */
    private Boolean hadNon;

    /**
     * 是否解析过sql
     * <p>
     * hadAll = true || hadNon = true时，
     * 即使不需要修改sql，parsed = true
     */
    private Boolean parsed;

    public static SqlHandlerResult hadAll() {
        SqlHandlerResult result = new SqlHandlerResult();
        result.setHadAll(true);
        result.setHadNon(false);
        result.setParsed(false);
        return result;
    }

    public static SqlHandlerResult hadNon() {
        SqlHandlerResult result = new SqlHandlerResult();
        result.setHadAll(false);
        result.setHadNon(true);
        result.setParsed(true);
        return result;
    }

    public static SqlHandlerResult nonData(boolean parsed) {
        SqlHandlerResult result = new SqlHandlerResult();
        result.setHadAll(false);
        result.setHadNon(parsed);
        result.setParsed(parsed);
        return result;
    }

    public static SqlHandlerResult handle() {
        SqlHandlerResult result = new SqlHandlerResult();
        result.setHadAll(false);
        result.setHadNon(false);
        result.setParsed(true);
        return result;
    }

    private SqlHandlerResult() {
    }

}
