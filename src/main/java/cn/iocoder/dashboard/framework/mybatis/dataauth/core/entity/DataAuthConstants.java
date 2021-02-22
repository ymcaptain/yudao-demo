package cn.iocoder.dashboard.framework.mybatis.dataauth.core.entity;

import org.slf4j.helpers.MessageFormatter;

/**
 * 数据权限限制常量类
 *
 * @author zzf
 * @date 2021/2/22 14:21
 */
public interface DataAuthConstants {

    String DEFAULT_ALIAS = "df_als";

    String ERROR_MSG_NO_TABLE_INFO = "entity class [{}] TableInfo cannot be null, make sure MP can load that mapper.";

    String ERROR_MSG_NO_FIELD_INFO = "can not found field [{}] corresponding field name.";

    static String notExistsTableInfo(String entityClassName) {
        return MessageFormatter.format(ERROR_MSG_NO_TABLE_INFO, entityClassName).getMessage();
    }

    static String notExistsFieldInfo(String fieldName) {
        return MessageFormatter.format(ERROR_MSG_NO_FIELD_INFO, fieldName).getMessage();
    }

}
