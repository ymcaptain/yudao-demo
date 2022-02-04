package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author aquan
 */
@ApiModel("修改商品状态 Request VO")
@Data
public class MallProductCategoryUpdateStatusReqVO {

    @ApiModelProperty(value = "商品类别编号", required = true, example = "1024")
    @NotNull(message = "商品类别编号不能为空")
    private Long id;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "见 SysCommonStatusEnum 枚举")
    @NotNull(message = "状态不能为空")
    private Integer status;

}
