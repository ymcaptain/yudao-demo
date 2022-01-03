package cn.iocoder.yudao.adminserver.modules.mall.convert.product;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrkey.*;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductAttrKeyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 商品规格键 Convert
 *
 * @author aquan
 */
@Mapper
public interface MallProductAttrKeyConvert {

    MallProductAttrKeyConvert INSTANCE = Mappers.getMapper(MallProductAttrKeyConvert.class);

    MallProductAttrKeyDO convert(MallProductAttrKeyCreateReqVO bean);

    MallProductAttrKeyDO convert(MallProductAttrKeyUpdateStatusReqVO bean);

    MallProductAttrKeyDO convert(MallProductAttrKeyUpdateReqVO bean);

    MallProductAttrKeyRespVO convert(MallProductAttrKeyDO bean);

    List<MallProductAttrKeyRespVO> convertList(List<MallProductAttrKeyDO> list);

    List<MallProductAttrKeySimpleRespVO> convertSimpleList(List<MallProductAttrKeyDO> list);

    PageResult<MallProductAttrKeyRespVO> convertPage(PageResult<MallProductAttrKeyDO> page);

    List<MallProductAttrKeyExcelVO> convertList02(List<MallProductAttrKeyDO> list);

}
