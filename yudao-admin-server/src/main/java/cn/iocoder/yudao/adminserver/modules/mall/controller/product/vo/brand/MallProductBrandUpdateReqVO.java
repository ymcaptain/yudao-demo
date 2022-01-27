package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.brand;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author aquan
 */
@ApiModel("商品品牌更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MallProductBrandUpdateReqVO extends MallProductBrandBaseVO {

    @ApiModelProperty(value = "品牌编号", required = true)
    @NotNull(message = "品牌编号不能为空")
    private Long id;

}
