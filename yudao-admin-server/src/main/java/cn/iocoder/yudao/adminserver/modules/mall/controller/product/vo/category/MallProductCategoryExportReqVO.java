package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author aquan
 */
@ApiModel(value = "商品分类 Excel 导出 Request VO", description = "参数和 MallProductCategoryPageReqVO 是一致的")
@Data
public class MallProductCategoryExportReqVO {

    @ApiModelProperty(value = "父分类编号")
    private Long pid;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "状态")
    private Integer status;

}
