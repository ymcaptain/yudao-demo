package cn.iocoder.yudao.adminserver.modules.mall.service.product;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category.*;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 商品分类 Service 接口
 *
 * @author aquan
 */
public interface MallProductCategoryService {

    /**
     * 创建商品分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductCategory(@Valid MallProductCategoryCreateReqVO createReqVO);

    /**
     * 更新商品分类
     *
     * @param updateReqVO 更新信息
     */
    void updateProductCategory(@Valid MallProductCategoryUpdateReqVO updateReqVO);

    /**
     * 更新商品分类状态
     *
     * @param updateReqVO 更新信息
     */
    void updateProductBrandStatus(MallProductCategoryUpdateStatusReqVO updateReqVO);

    /**
     * 删除商品分类
     *
     * @param id 编号
     */
    void deleteProductCategory(Long id);

    /**
     * 获得商品分类
     *
     * @param id 编号
     * @return 商品分类
     */
    MallProductCategoryDO getProductCategory(Long id);

    /**
     * 获取顶级商品分类简单信息列表
     *
     * @return 顶级商品分类列表
     */
    List<MallProductCategoryDO> getProductCategoryRoots();

    /**
     * 获得商品分类列表
     *
     * @param ids 编号
     * @return 商品分类列表
     */
    List<MallProductCategoryDO> getProductCategoryList(Collection<Long> ids);

    /**
     * 获得商品分类分页
     *
     * @param pageReqVO 分页查询
     * @return 商品分类分页
     */
    PageResult<MallProductCategoryDO> getProductCategoryPage(MallProductCategoryPageReqVO pageReqVO);

    /**
     * 获得商品分类列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 商品分类列表
     */
    List<MallProductCategoryDO> getProductCategoryList(MallProductCategoryExportReqVO exportReqVO);

}
