package cn.iocoder.yudao.adminserver.modules.bpm.service.oa;


import cn.iocoder.yudao.adminserver.modules.bpm.controller.oa.vo.*;
import cn.iocoder.yudao.adminserver.modules.bpm.dal.dataobject.leave.OALeaveDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 请假申请 Service 接口
 *
 * @author 芋艿
 */
public interface OALeaveService {

    /**
     * 创建请假申请
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createLeave(@Valid OALeaveCreateReqVO createReqVO);

    /**
     * 更新请假申请
     *
     * @param updateReqVO 更新信息
     */
    void updateLeave(@Valid OALeaveUpdateReqVO updateReqVO);

    /**
     * 删除请假申请
     *
     * @param id 编号
     */
    void deleteLeave(Long id);

    /**
     * 获得请假申请
     *
     * @param id 编号
     * @return 请假申请
     */
    OALeaveDO getLeave(Long id);

    /**
     * 获得请假申请列表
     *
     * @param ids 编号
     * @return 请假申请列表
     */
    List<OALeaveDO> getLeaveList(Collection<Long> ids);

    /**
     * 获得请假申请分页
     *
     * @param pageReqVO 分页查询
     * @return 请假申请分页
     */
    PageResult<OALeaveDO> getLeavePage(OALeavePageReqVO pageReqVO);

    /**
     * 获得请假申请列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 请假申请列表
     */
    List<OALeaveDO> getLeaveList(OALeaveExportReqVO exportReqVO);



    /**
     * 获取本人请假申请流程中审批人员
     * @return  包括，本人部门的项目经理， 部门经理，  HR
     */
    OALeaveApplyMembersVO getLeaveApplyMembers();
}
