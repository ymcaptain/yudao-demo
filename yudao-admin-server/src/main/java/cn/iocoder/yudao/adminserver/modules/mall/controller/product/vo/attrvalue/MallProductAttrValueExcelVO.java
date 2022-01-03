package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrvalue;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 商品规格值 Excel VO
 *
 * @author aquan
 */
@Data
public class MallProductAttrValueExcelVO {

    @ExcelProperty("规格值编号")
    private Long id;

    /**
     * 规格键编号
     */
    @ExcelIgnore
    private Long attrKeyId;

    @ExcelProperty("规格键名称")
    private String attrKeyName;

    @ExcelProperty("规格值名字")
    private String name;

    @DictFormat("sys_common_status")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    private Integer status;

    @ExcelProperty("创建时间")
    private Date createTime;

}
