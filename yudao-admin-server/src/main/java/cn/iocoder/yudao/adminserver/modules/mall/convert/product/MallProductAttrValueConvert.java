package cn.iocoder.yudao.adminserver.modules.mall.convert.product;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrvalue.*;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductAttrValueDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 商品规格值 Convert
 *
 * @author aquan
 */
@Mapper
public interface MallProductAttrValueConvert {

    MallProductAttrValueConvert INSTANCE = Mappers.getMapper(MallProductAttrValueConvert.class);

    MallProductAttrValueDO convert(MallProductAttrValueCreateReqVO bean);

    MallProductAttrValueDO convert(MallProductAttrValueUpdateReqVO bean);

    MallProductAttrValueRespVO convert(MallProductAttrValueDO bean);

    MallProductAttrValueDO convert(MallProductAttrValueUpdateStatusReqVO bean);

    List<MallProductAttrValueRespVO> convertList(List<MallProductAttrValueDO> list);

    PageResult<MallProductAttrValueRespVO> convertPage(PageResult<MallProductAttrValueDO> page);

    List<MallProductAttrValueExcelVO> convertList02(List<MallProductAttrValueDO> list);

}
