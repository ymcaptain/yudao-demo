package cn.iocoder.yudao.module.design.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * Infra 错误码枚举类
 *
 * infra 系统，使用 1-001-000-000 段
 */
public interface ErrorCodeConstants {
    // ========== 数据大屏 1103001000 ==========
    ErrorCode SCREEN_NOT_EXISTS = new ErrorCode(1103001001, "数据大屏不存在");
    ErrorCode SCREEN_CODE_ERROR = new ErrorCode(1103001002, "访问码错误");

}
