package cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 商品品牌 DO
 *
 * @author aquan
 */
@TableName("mall_product_brand")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MallProductBrandDO extends BaseDO {

    /**
     * 品牌编号
     */
    @TableId
    private Long id;
    /**
     * 品牌名称
     */
    private String name;
    /**
     * 品牌描述
     */
    private String description;
    /**
     * 品牌名图片
     */
    private String picUrl;
    /**
     * 状态
     */
    private Integer status;

}
