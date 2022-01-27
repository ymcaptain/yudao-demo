package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.brand;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author aquan
 */
@ApiModel("商品品牌 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MallProductBrandRespVO extends MallProductBrandBaseVO {

    @ApiModelProperty(value = "品牌编号", required = true)
    private Integer id;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
