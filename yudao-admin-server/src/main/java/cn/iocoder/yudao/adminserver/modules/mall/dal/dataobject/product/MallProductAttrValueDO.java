package cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 商品规格值 DO
 *
 * @author aquan
 */
@TableName("mall_product_attr_value")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MallProductAttrValueDO extends BaseDO {

    /**
     * 规格值编号
     */
    @TableId
    private Long id;
    /**
     * 规格键编号
     */
    private Long attrKeyId;
    /**
     * 规格值名字
     */
    private String name;
    /**
     * 状态
     */
    private Integer status;

}
