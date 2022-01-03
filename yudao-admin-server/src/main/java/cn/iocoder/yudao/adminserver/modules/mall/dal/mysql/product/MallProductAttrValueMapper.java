package cn.iocoder.yudao.adminserver.modules.mall.dal.mysql.product;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrvalue.MallProductAttrValueExportReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrvalue.MallProductAttrValuePageReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductAttrValueDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品规格值 Mapper
 *
 * @author aquan
 */
@Mapper
public interface MallProductAttrValueMapper extends BaseMapperX<MallProductAttrValueDO> {

    default PageResult<MallProductAttrValueDO> selectPage(MallProductAttrValuePageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<MallProductAttrValueDO>()
                .eqIfPresent("attr_key_id", reqVO.getAttrKeyId())
                .likeIfPresent("name", reqVO.getName())
                .eqIfPresent("status", reqVO.getStatus())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id"));
    }

    default List<MallProductAttrValueDO> selectList(MallProductAttrValueExportReqVO reqVO) {
        return selectList(new QueryWrapperX<MallProductAttrValueDO>()
                .eqIfPresent("attr_key_id", reqVO.getAttrKeyId())
                .likeIfPresent("name", reqVO.getName())
                .eqIfPresent("status", reqVO.getStatus())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id"));
    }

    default Long selectCount(Long attrKeyId) {
        return selectCount(new LambdaQueryWrapper<MallProductAttrValueDO>()
                .eq(MallProductAttrValueDO::getAttrKeyId, attrKeyId));
    }

}
