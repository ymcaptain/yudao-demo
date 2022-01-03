package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrvalue;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author aquan
 */
@ApiModel("商品规格值创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MallProductAttrValueCreateReqVO extends MallProductAttrValueBaseVO {

}
