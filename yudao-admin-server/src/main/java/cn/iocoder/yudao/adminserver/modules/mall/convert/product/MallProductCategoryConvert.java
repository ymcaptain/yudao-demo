package cn.iocoder.yudao.adminserver.modules.mall.convert.product;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category.*;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 商品分类 Convert
 *
 * @author aquan
 */
@Mapper
public interface MallProductCategoryConvert {

    MallProductCategoryConvert INSTANCE = Mappers.getMapper(MallProductCategoryConvert.class);

    MallProductCategoryDO convert(MallProductCategoryUpdateStatusReqVO bean);

    MallProductCategoryDO convert(MallProductCategoryCreateReqVO bean);

    MallProductCategoryDO convert(MallProductCategoryUpdateReqVO bean);

    MallProductCategoryRespVO convert(MallProductCategoryDO bean);

    List<MallProductCategoryRespVO> convertList(List<MallProductCategoryDO> list);

    List<MallProductSimpleCategoryRespVO> convertRootList(List<MallProductCategoryDO> list);

    PageResult<MallProductCategoryRespVO> convertPage(PageResult<MallProductCategoryDO> page);

    List<MallProductCategoryExcelVO> convertList02(List<MallProductCategoryDO> list);

}
