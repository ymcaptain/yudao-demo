package cn.iocoder.yudao.adminserver.modules.mall.dal.mysql.product;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrkey.MallProductAttrKeyExportReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrkey.MallProductAttrKeyPageReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductAttrKeyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品规格键 Mapper
 *
 * @author aquan
 */
@Mapper
public interface MallProductAttrKeyMapper extends BaseMapperX<MallProductAttrKeyDO> {

    default PageResult<MallProductAttrKeyDO> selectPage(MallProductAttrKeyPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<MallProductAttrKeyDO>()
                .likeIfPresent("name", reqVO.getName())
                .eqIfPresent("status", reqVO.getStatus())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id"));
    }

    default List<MallProductAttrKeyDO> selectList(MallProductAttrKeyExportReqVO reqVO) {
        return selectList(new QueryWrapperX<MallProductAttrKeyDO>()
                .likeIfPresent("name", reqVO.getName())
                .eqIfPresent("status", reqVO.getStatus())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id"));
    }

}
