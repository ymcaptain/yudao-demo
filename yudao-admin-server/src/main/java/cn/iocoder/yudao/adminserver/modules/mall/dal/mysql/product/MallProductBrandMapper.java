package cn.iocoder.yudao.adminserver.modules.mall.dal.mysql.product;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.brand.MallProductBrandExportReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.brand.MallProductBrandPageReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductBrandDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品品牌 Mapper
 *
 * @author aquan
 */
@Mapper
public interface MallProductBrandMapper extends BaseMapperX<MallProductBrandDO> {

    default PageResult<MallProductBrandDO> selectPage(MallProductBrandPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<MallProductBrandDO>()
                .likeIfPresent("name", reqVO.getName())
                .eqIfPresent("status", reqVO.getStatus())
                .orderByDesc("id"));
    }

    default List<MallProductBrandDO> selectList(MallProductBrandExportReqVO reqVO) {
        return selectList(new QueryWrapperX<MallProductBrandDO>()
                .likeIfPresent("name", reqVO.getName())
                .eqIfPresent("status", reqVO.getStatus())
                .orderByDesc("id"));
    }

}
