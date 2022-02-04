package cn.iocoder.yudao.adminserver.modules.mall.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * mall 商城模块错误码
 *
 * @author aquan
 */
public interface MallErrorCodeConstants {

    /**
     * ========== 商品规格模块 1-008-000-000 ==========
     */
    ErrorCode PRODUCT_ATTR_KEY_NOT_EXISTS = new ErrorCode(1008000001, "商品规格键不存在");

    ErrorCode PRODUCT_ATTR_VALUE_NOT_EXISTS = new ErrorCode(1008000002, "商品规格值不存在");

    ErrorCode PRODUCT_ATTR_KEY_EXIST_VALUES_CANT_DELETE = new ErrorCode(1008000002, "商品规格键存在商品规格值无法删除");

    /**
     * ========== 商品品牌模块 1-009-000-000 ==========
     */
    ErrorCode PRODUCT_BRAND_NOT_EXISTS = new ErrorCode(1009000001, "商品品牌不存在");

    /**
     * ========== 商品分类模块 1-010-000-000 ==========
     */
    ErrorCode PRODUCT_CATEGORY_NOT_EXISTS = new ErrorCode(1010000001, "商品分类不存在");

    ErrorCode PRODUCT_CATEGORY_LEAVERS_EXISTS_CANT_DELETE = new ErrorCode(1010000002, "当前商品分类存在子分类,无法删除");

}
