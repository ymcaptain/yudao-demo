package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.brand;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 商品品牌 Excel VO
 *
 * @author aquan
 */
@Data
public class MallProductBrandExcelVO {

    @ExcelProperty("品牌编号")
    private Integer id;

    @ExcelProperty("品牌名称")
    private String name;

    @ExcelProperty("品牌描述")
    private String description;

    @ExcelProperty("品牌名图片")
    private String picUrl;

    @DictFormat("sys_common_status")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    private Integer status;

    @ExcelProperty("创建时间")
    private Date createTime;

}
