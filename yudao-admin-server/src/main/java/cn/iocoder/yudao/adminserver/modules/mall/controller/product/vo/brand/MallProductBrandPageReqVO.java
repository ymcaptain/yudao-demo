package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.brand;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author aquan
 */
@ApiModel("商品品牌分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MallProductBrandPageReqVO extends PageParam {

    @ApiModelProperty(value = "品牌名称")
    private String name;

    @ApiModelProperty(value = "状态")
    private Integer status;

}
