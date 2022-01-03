package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrkey;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 商品规格键 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 *
 * @author aquan
 */
@Data
public class MallProductAttrKeyBaseVO {

    @ApiModelProperty(value = "规格键名称", required = true)
    @NotNull(message = "规格键名称不能为空")
    private String name;

    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "状态")
    private Integer status;

}
