package cn.iocoder.dashboard.framework.mybatis.dataauth;
/**
 * 数据权限限制SQL自动注入
 *
 * 1、利用mp的mapper扫描将带有需要限制和不需要限制的类/方法信息缓存。
 * @see cn.iocoder.dashboard.framework.mybatis.dataauth.core.component.MySqlInjector
 *
 * 2、利用mp的拦截器对执行的方法拦截，并通过1中的信息判断是否需要进行限制。
 * @see cn.iocoder.dashboard.framework.mybatis.dataauth.core.component.DataAuthInterceptor
 *
 * 3、配置数据权限限制的处理器以及处理策略
 * @see cn.iocoder.dashboard.framework.mybatis.dataauth.core.component.AbstractDataAuthSqlHandler
 * @see cn.iocoder.dashboard.framework.mybatis.dataauth.core.component.DataAuthStrategy
 *
 * 4、在系统初始化完成后，加载权限限制的表字短信，并全局缓存。
 * @see cn.iocoder.dashboard.framework.mybatis.dataauth.core.component.DataAuthCacheHelper
 * @see cn.iocoder.dashboard.framework.mybatis.dataauth.core.entity.DataAuthCache
 *
 */