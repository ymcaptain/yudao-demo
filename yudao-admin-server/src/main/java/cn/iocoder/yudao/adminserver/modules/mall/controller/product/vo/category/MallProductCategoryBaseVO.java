package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 商品分类 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 *
 * @author aquan
 */
@Data
public class MallProductCategoryBaseVO {

    @ApiModelProperty(value = "父分类编号", required = true)
    @NotNull(message = "父分类编号不能为空")
    private Long pid;

    @ApiModelProperty(value = "分类名称", required = true)
    @NotNull(message = "分类名称不能为空")
    private String name;

    @ApiModelProperty(value = "分类描述")
    private String description;

    @ApiModelProperty(value = "分类图片")
    private String picUrl;

    @ApiModelProperty(value = "分类排序", required = true)
    @NotNull(message = "分类排序不能为空")
    private Integer sort;

    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "状态不能为空")
    private Integer status;

}
