package cn.iocoder.yudao.module.design.service.screen;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.design.controller.admin.screen.vo.*;
import cn.iocoder.yudao.module.design.dal.dataobject.screen.ScreenDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 数据大屏 Service 接口
 *
 * @author Timeless小帅
 */
public interface ScreenService {

    /**
     * 创建数据大屏
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createScreen(@Valid ScreenCreateReqVO createReqVO);

    /**
     * 更新数据大屏
     *
     * @param updateReqVO 更新信息
     */
    void updateScreen(@Valid ScreenUpdateReqVO updateReqVO);

    /**
     * 删除数据大屏
     *
     * @param id 编号
     */
    void deleteScreen(Long id);

    /**
     * 获得数据大屏
     *
     * @param id 编号
     * @return 数据大屏
     */
    ScreenDO getScreen(Long id);

    /**
     * 获得数据大屏列表
     *
     * @param ids 编号
     * @return 数据大屏列表
     */
    List<ScreenDO> getScreenList(Collection<Long> ids);

    /**
     * 获得数据大屏分页
     *
     * @param pageReqVO 分页查询
     * @return 数据大屏分页
     */
    PageResult<ScreenDO> getScreenPage(ScreenPageReqVO pageReqVO);

    /**
     * 获得数据大屏列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 数据大屏列表
     */
    List<ScreenDO> getScreenList(ScreenExportReqVO exportReqVO);

}
