package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
 * @author aquan
 */
@ApiModel("商品分类更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MallProductCategoryUpdateReqVO extends MallProductCategoryBaseVO {

    @ApiModelProperty(value = "分类编号", required = true)
    @NotNull(message = "分类编号不能为空")
    private Long id;

}
