package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * @author aquan
 */
@ApiModel("商品分类分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MallProductCategoryPageReqVO extends PageParam {

    @ApiModelProperty(value = "父分类编号,不传递默认为根目录")
    private Long pid;

    @ApiModelProperty(value = "分类名称，查询当前目录下的")
    private String name;

    @ApiModelProperty(value = "状态")
    private Integer status;

}
