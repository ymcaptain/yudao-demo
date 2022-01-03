package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrvalue;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author aquan
 */
@ApiModel("修改商品规格值状态 Request VO")
@Data
public class MallProductAttrValueUpdateStatusReqVO {

    @ApiModelProperty(value = "规格值编号", required = true, example = "1024")
    @NotNull(message = "规格值编号不能为空")
    private Long id;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "见 SysCommonStatusEnum 枚举")
    @NotNull(message = "状态不能为空")
    private Integer status;

}
