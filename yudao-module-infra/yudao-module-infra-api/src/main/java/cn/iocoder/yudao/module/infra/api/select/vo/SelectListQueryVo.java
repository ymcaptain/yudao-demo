package cn.iocoder.yudao.module.infra.api.select.vo;

import com.sun.istack.internal.NotNull;
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
}
