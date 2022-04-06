package cn.iocoder.yudao.framework.common.util.string;

import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;

// TODO @宋康帅：放到 mybatis 里，属于 db 那的工具类。引用工具类使，要保证代码的规范性
/**
 * sql操作工具类
 *
 * @author ruoyi
 */
public class SqlUtil
{
    /**
     * 定义常用的 sql关键字
     */
    public static String SQL_REGEX = "select |insert |delete |update |drop |count |exec |chr |mid |master |truncate |char |and |declare ";

    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";


    /**
     * 判断词是否危险
     * @param value 要检验的值
     * @return true:危险
     */
    public static boolean isWarnWord(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return false;
        }

        // TODO @宋康帅：@SQL_WORD_ERROR：挪到 mybatis 自己搞的错误码
        if (!value.matches(SQL_PATTERN)){
            throw new ServiceException(GlobalErrorCodeConstants.SQL_WORD_ERROR);
        }

        return isHasSqlKeyword(value);
    }

    /**
     * 是否存在SQL关键字
     * @param value 要检验的值
     * @return true:危险
     */
    public static boolean isHasSqlKeyword(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return false;
        }
        String[] sqlKeywords = StringUtils.split(SQL_REGEX, "\\|");
        for (int i = 0; i < sqlKeywords.length; i++)
        {
            if (StringUtils.indexOfIgnoreCase(value, sqlKeywords[i]) > -1)
            {
                throw new ServiceException(GlobalErrorCodeConstants.SQL_WORD_ERROR);
            }
        }
        return false;
    }
}
