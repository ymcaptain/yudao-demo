package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrkey;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author aquan
 */
@Data
public class MallProductAttrKeyExcelVO {

    @ExcelProperty("规格键编号")
    private Long id;

    @ExcelProperty("规格键名称")
    private String name;

    @DictFormat("sys_common_status")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    private Integer status;

    @ExcelProperty("创建时间")
    private Date createTime;

}
