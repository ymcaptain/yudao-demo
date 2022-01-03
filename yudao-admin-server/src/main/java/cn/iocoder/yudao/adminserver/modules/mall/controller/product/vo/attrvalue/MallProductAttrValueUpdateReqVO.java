package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrvalue;

import lombok.*;

import java.util.*;

import io.swagger.annotations.*;

import javax.validation.constraints.*;

/**
 * @author aquan
 */
@ApiModel("商品规格值更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MallProductAttrValueUpdateReqVO extends MallProductAttrValueBaseVO {

    @ApiModelProperty(value = "规格值编号", required = true)
    @NotNull(message = "规格值编号不能为空")
    private Long id;

}
