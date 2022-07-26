package cn.iocoder.dashboard.framework.logger.operatelog.core.service;

import cn.iocoder.dashboard.modules.system.controller.logger.vo.operatelog.SysOperateLogCreateReqVO;

import java.util.concurrent.Future;

public interface OperateLogFrameworkService {

    /**
     * 异步记录操作日志
     *
     * @param reqVO 操作日志请求
     * @return true: 记录成功,false: 记录失败
     */
    Future<Boolean> createOperateLogAsync(SysOperateLogCreateReqVO reqVO);

}
