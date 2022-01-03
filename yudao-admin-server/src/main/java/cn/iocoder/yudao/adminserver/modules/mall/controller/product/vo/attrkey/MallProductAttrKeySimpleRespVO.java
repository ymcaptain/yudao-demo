package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrkey;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author aquan
 */
@Data
@ApiModel("商品规格键精简 Response VO")
public class MallProductAttrKeySimpleRespVO {

    @ApiModelProperty(value = "商品规格键ID")
    private Long id;

    @ApiModelProperty(value = "规格键名称", required = true)
    private String name;
}
