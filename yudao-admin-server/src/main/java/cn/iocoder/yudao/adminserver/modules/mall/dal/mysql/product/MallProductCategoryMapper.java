package cn.iocoder.yudao.adminserver.modules.mall.dal.mysql.product;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category.MallProductCategoryExportReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category.MallProductCategoryPageReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductCategoryDO;
import cn.iocoder.yudao.adminserver.modules.mall.enums.MallProductCategoryConstants;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品分类 Mapper
 *
 * @author aquan
 */
@Mapper
public interface MallProductCategoryMapper extends BaseMapperX<MallProductCategoryDO> {

    default PageResult<MallProductCategoryDO> selectPage(MallProductCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<MallProductCategoryDO>()
                .eqIfPresent("pid", reqVO.getPid())
                .likeIfPresent("name", reqVO.getName())
                .eqIfPresent("status", reqVO.getStatus())
                .orderByDesc("id"));
    }

    default List<MallProductCategoryDO> selectList(MallProductCategoryExportReqVO reqVO) {
        return selectList(new QueryWrapperX<MallProductCategoryDO>()
                .eqIfPresent("pid", reqVO.getPid())
                .likeIfPresent("name", reqVO.getName())
                .eqIfPresent("status", reqVO.getStatus())
                .orderByDesc("id"));
    }

    /**
     * 查询顶级商品分类列表
     *
     * @return 顶级商品分类列表
     */
    default List<MallProductCategoryDO> selectRoots(){
        return selectList(new LambdaQueryWrapper<MallProductCategoryDO>()
                .eq(MallProductCategoryDO::getPid, MallProductCategoryConstants.CATEGORY_ROOT_PID));
    }

    /**
     * 查询当前类别下的子类目数量
     * @param id 类目 ID
     * @return 子类目数量
     */
    default Long selectLeavesCount(Long id) {
        return selectCount(new LambdaQueryWrapper<MallProductCategoryDO>().eq(MallProductCategoryDO::getPid,id));
    }
}
