package cn.iocoder.yudao.module.design.dal.mysql.screen;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.design.dal.dataobject.screen.ScreenDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.design.controller.admin.screen.vo.*;

/**
 * 数据大屏 Mapper
 *
 * @author Timeless小帅
 */
@Mapper
public interface ScreenMapper extends BaseMapperX<ScreenDO> {

    default PageResult<ScreenDO> selectPage(ScreenPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ScreenDO>()
                .eqIfPresent(ScreenDO::getTitle, reqVO.getTitle())
                .eqIfPresent(ScreenDO::getScaleX, reqVO.getScaleX())
                .eqIfPresent(ScreenDO::getScaleY, reqVO.getScaleY())
                .eqIfPresent(ScreenDO::getDesignImgId, reqVO.getDesignImgId())
                .eqIfPresent(ScreenDO::getState, reqVO.getState())
                .eqIfPresent(ScreenDO::getViewCode, reqVO.getViewCode())
                .eqIfPresent(ScreenDO::getCountView, reqVO.getCountView())
                .betweenIfPresent(ScreenDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(ScreenDO::getId));
    }

    default List<ScreenDO> selectList(ScreenExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ScreenDO>()
                .eqIfPresent(ScreenDO::getTitle, reqVO.getTitle())
                .eqIfPresent(ScreenDO::getScaleX, reqVO.getScaleX())
                .eqIfPresent(ScreenDO::getScaleY, reqVO.getScaleY())
                .eqIfPresent(ScreenDO::getDesignImgId, reqVO.getDesignImgId())
                .eqIfPresent(ScreenDO::getState, reqVO.getState())
                .eqIfPresent(ScreenDO::getViewCode, reqVO.getViewCode())
                .eqIfPresent(ScreenDO::getCountView, reqVO.getCountView())
                .betweenIfPresent(ScreenDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(ScreenDO::getId));
    }

}
