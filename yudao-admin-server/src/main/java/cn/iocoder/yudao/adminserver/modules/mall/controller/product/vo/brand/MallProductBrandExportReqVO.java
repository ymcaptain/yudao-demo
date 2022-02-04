package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.brand;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author aquan
 */
@ApiModel(value = "商品品牌 Excel 导出 Request VO", description = "参数和 MallProductBrandPageReqVO 是一致的")
@Data
public class MallProductBrandExportReqVO {

    @ApiModelProperty(value = "品牌名称")
    private String name;

    @ApiModelProperty(value = "状态")
    private Integer status;

}
