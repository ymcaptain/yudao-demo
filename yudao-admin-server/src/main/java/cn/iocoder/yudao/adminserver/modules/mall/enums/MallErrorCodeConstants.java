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
}
