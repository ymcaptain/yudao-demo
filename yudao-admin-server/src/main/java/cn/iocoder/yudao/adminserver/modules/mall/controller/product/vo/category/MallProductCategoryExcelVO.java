package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 商品分类 Excel VO
 *
 * @author aquan
 */
@Data
public class MallProductCategoryExcelVO {

    @ExcelProperty("分类编号")
    private Long id;

    @ExcelProperty("父分类编号")
    private Long pid;

    @ExcelProperty("分类名称")
    private String name;

    @ExcelProperty("分类描述")
    private String description;

    @ExcelProperty("分类图片")
    private String picUrl;

    @ExcelProperty("分类排序")
    private Integer sort;

    @DictFormat("sys_common_status")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    private Integer status;

    @ExcelProperty("创建时间")
    private Date createTime;

}
