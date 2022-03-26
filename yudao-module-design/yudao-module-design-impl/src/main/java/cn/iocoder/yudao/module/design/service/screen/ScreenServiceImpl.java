package cn.iocoder.yudao.module.design.service.screen;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.design.controller.admin.screen.vo.*;
import cn.iocoder.yudao.module.design.dal.dataobject.screen.ScreenDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.design.convert.screen.ScreenConvert;
import cn.iocoder.yudao.module.design.dal.mysql.screen.ScreenMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.design.enums.ErrorCodeConstants.*;

/**
 * 数据大屏 Service 实现类
 *
 * @author Timeless小帅
 */
@Service
@Validated
public class ScreenServiceImpl implements ScreenService {

    @Resource
    private ScreenMapper screenMapper;

    @Override
    public Long createScreen(ScreenCreateReqVO createReqVO) {
        // 插入
        ScreenDO screen = ScreenConvert.INSTANCE.convert(createReqVO);
        screenMapper.insert(screen);
        // 返回
        return screen.getId();
    }

    @Override
    public void updateScreen(ScreenUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateScreenExists(updateReqVO.getId());
        // 更新
        ScreenDO updateObj = ScreenConvert.INSTANCE.convert(updateReqVO);
        screenMapper.updateById(updateObj);
    }

    @Override
    public void deleteScreen(Long id) {
        // 校验存在
        this.validateScreenExists(id);
        // 删除
        screenMapper.deleteById(id);
    }

    private void validateScreenExists(Long id) {
        if (screenMapper.selectById(id) == null) {
            throw exception(SCREEN_NOT_EXISTS);
        }
    }

    @Override
    public ScreenDO getScreen(Long id) {
        return screenMapper.selectById(id);
    }

    @Override
    public List<ScreenDO> getScreenList(Collection<Long> ids) {
        return screenMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ScreenDO> getScreenPage(ScreenPageReqVO pageReqVO) {
        return screenMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ScreenDO> getScreenList(ScreenExportReqVO exportReqVO) {
        return screenMapper.selectList(exportReqVO);
    }

}
