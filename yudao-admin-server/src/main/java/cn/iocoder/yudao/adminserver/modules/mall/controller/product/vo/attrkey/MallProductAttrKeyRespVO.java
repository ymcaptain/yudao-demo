package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrkey;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author aquan
 */
@ApiModel("商品规格键 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MallProductAttrKeyRespVO extends MallProductAttrKeyBaseVO {

    @ApiModelProperty(value = "规格键编号", required = true)
    private Long id;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
