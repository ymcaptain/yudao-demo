package cn.iocoder.yudao.adminserver.modules.mall.convert.product;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.brand.*;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductBrandDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 商品品牌 Convert
 *
 * @author aquan
 */
@Mapper
public interface MallProductBrandConvert {

    MallProductBrandConvert INSTANCE = Mappers.getMapper(MallProductBrandConvert.class);

    MallProductBrandDO convert(MallProductBrandCreateReqVO bean);

    MallProductBrandDO convert(MallProductBrandUpdateReqVO bean);

    MallProductBrandDO convert(MallProductBrandUpdateStatusReqVO bean);

    MallProductBrandRespVO convert(MallProductBrandDO bean);

    List<MallProductBrandRespVO> convertList(List<MallProductBrandDO> list);

    PageResult<MallProductBrandRespVO> convertPage(PageResult<MallProductBrandDO> page);

    List<MallProductBrandExcelVO> convertList02(List<MallProductBrandDO> list);

}
