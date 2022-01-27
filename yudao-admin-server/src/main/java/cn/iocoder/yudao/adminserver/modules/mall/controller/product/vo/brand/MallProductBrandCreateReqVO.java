package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.brand;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author aquan
 */
@ApiModel("商品品牌创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MallProductBrandCreateReqVO extends MallProductBrandBaseVO {

}
