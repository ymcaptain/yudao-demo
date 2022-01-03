package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrkey;

import lombok.*;
import io.swagger.annotations.*;

import javax.validation.constraints.*;

/**
 * @author aquan
 */
@ApiModel("商品规格键更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MallProductAttrKeyUpdateReqVO extends MallProductAttrKeyBaseVO {

    @ApiModelProperty(value = "规格键编号", required = true)
    @NotNull(message = "规格键编号不能为空")
    private Long id;

}
