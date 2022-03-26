package cn.iocoder.yudao.module.design.convert.screen;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.design.controller.admin.screen.vo.*;
import cn.iocoder.yudao.module.design.dal.dataobject.screen.ScreenDO;

/**
 * 数据大屏 Convert
 *
 * @author Timeless小帅
 */
@Mapper
public interface ScreenConvert {

    ScreenConvert INSTANCE = Mappers.getMapper(ScreenConvert.class);

    ScreenDO convert(ScreenCreateReqVO bean);

    ScreenDO convert(ScreenUpdateReqVO bean);

    ScreenRespVO convert(ScreenDO bean);

    ScreenUpdateReqVO convertUpdate(ScreenDO bean);

    List<ScreenRespVO> convertList(List<ScreenDO> list);

    PageResult<ScreenRespVO> convertPage(PageResult<ScreenDO> page);

    List<ScreenExcelVO> convertList02(List<ScreenDO> list);

}
