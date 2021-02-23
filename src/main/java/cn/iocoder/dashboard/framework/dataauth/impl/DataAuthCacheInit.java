package cn.iocoder.dashboard.framework.dataauth.impl;

import cn.iocoder.dashboard.framework.dataauth.core.component.DataAuthCacheHelper;
import cn.iocoder.dashboard.modules.system.dal.dataobject.user.SysUserDO;
import cn.iocoder.dashboard.modules.system.service.permission.SysPermissionService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 添加数据权限限制配置
 *
 * @author zzf
 * @date 2020/12/21 11:45
 */
@Component
public class DataAuthCacheInit implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private SysPermissionService permissionService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        DataAuthCacheHelper helper = new DataAuthCacheHelper()
                .setSqlHandler(new MyRoleDataAuthSqlHandler(permissionService))
                .addTargetEntityClass(SysUserDO.class)
                .setTargetFieldName(SysUserDO::getId)
                .bind();
        helper.cache();
    }

}
