package cn.iocoder.dashboard.framework.dataauth;
/**
 * 数据权限限制SQL自动注入
 * <p>
 * 1、利用mp的mapper扫描将带有需要限制和不需要限制的类/方法信息缓存。
 *
 * @see cn.iocoder.dashboard.framework.dataauth.core.component.DataAuthSqlInjector
 * <p>
 * 2、利用mp的拦截器对执行的方法拦截，并通过1中的信息判断是否需要进行限制。
 * @see cn.iocoder.dashboard.framework.dataauth.core.component.DataAuthInterceptor
 * <p>
 * 3、配置数据权限限制的处理器以及处理策略
 * @see cn.iocoder.dashboard.framework.dataauth.core.component.AbstractDataAuthSqlHandler
 * @see cn.iocoder.dashboard.framework.dataauth.core.component.DataAuthStrategy
 * <p>
 * 4、在系统初始化完成后，加载权限限制的表字短信，并全局缓存。
 * @see cn.iocoder.dashboard.framework.dataauth.core.component.DataAuthCacheHelper
 * @see cn.iocoder.dashboard.framework.dataauth.core.entity.DataAuthCache
 * @see cn.iocoder.dashboard.framework.dataauth.impl.DataAuthCacheInit
 * <p>
 * 使用：
 * 1、添加@DataAuth注解
 * @see cn.iocoder.dashboard.modules.system.dal.mysql.user.SysUserMapper#selectPage(cn.iocoder.dashboard.modules.system.controller.user.vo.user.SysUserPageReqVO, java.util.Collection)
 * 2、登录后请求对应接口即可。
 * 3、日志中有输出前后sql
 */