package cn.iocoder.dashboard.framework.mybatis.dataauth.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限sql处理器id枚举类
 *
 * @author zzf
 * @date 2021/2/19 15:51
 */
@Getter
@AllArgsConstructor
public enum SqlHandlerIdEnum {

    ROLE(1);

    final int id;
}
