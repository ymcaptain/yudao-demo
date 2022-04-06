package cn.iocoder.yudao.module.infra.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Infra 字典类型的枚举类
 *
 * @author 芋道源码
 */
public interface DictTypeConstants {

    String REDIS_TIMEOUT_TYPE = "infra_redis_timeout_type"; // Redis 超时类型

    String JOB_STATUS = "infra_job_status"; // 定时任务状态的枚举
    String JOB_LOG_STATUS = "infra_job_log_status"; // 定时任务日志状态的枚举

    String API_ERROR_LOG_PROCESS_STATUS = "infra_api_error_log_process_status"; // API 错误日志的处理状态的枚举

    String CONFIG_TYPE = "infra_config_type"; // 参数配置类型
    String BOOLEAN_STRING = "infra_boolean_string"; // Boolean 是否类型

    // TODO @宋康帅：单独一个枚举类
    @AllArgsConstructor
    @Getter
    enum GROUP_TYPE_ENUM{

        OtherGroup(0L,"其他","design_img_group"),
        Background(1L,"背景","design_img_group"),
        Image(2L,"图片库","design_img_group"),
        Material(3L,"素材","design_img_group"),
        Border(4L,"边框","design_img_group"),
        Icon(5L,"图标","design_img_group"),
        AVATAR(6L,"头像","design_img_group");

        private final Long id;
        private final String name;
        private final String dict;
    }
}
