package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author aquan
 */
@ApiModel("商品分类简单信息 Response VO")
@Data
@ToString(callSuper = true)
public class MallProductSimpleCategoryRespVO implements Serializable {

    @ApiModelProperty(value = "分类编号", required = true)
    private Long id;

    @ApiModelProperty(value = "分类名称", required = true)
    @NotNull(message = "分类名称不能为空")
    private String name;

}
