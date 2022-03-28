package cn.iocoder.yudao.module.infra.api.select.vo;

import cn.iocoder.yudao.framework.common.util.string.SqlUtil;
import lombok.Data;

/**
 * SelectListQueryVo:
 *
 * @author Timeless小帅
 * @date 2022/3/25
 */
@Data
public class SelectListQueryVo {
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 表id
     */
    private String id;
    /**
     * 表名称
     */
    private String name;
    /**
     * 条件前
     * where whereBegin = whereEnd
     */
    private String whereBegin;
    /**
     * 条件后
     * where whereBegin = whereEnd
     */
    private String whereEnd;

    /**
     * 是否有危险
     */
    public void isWarn(){
        SqlUtil.isWarnWord(tableName);
        SqlUtil.isWarnWord(id);
        SqlUtil.isWarnWord(name);
        SqlUtil.isWarnWord(whereBegin);
        SqlUtil.isWarnWord(whereEnd);
    }
}
