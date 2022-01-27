package cn.iocoder.yudao.adminserver.modules.mall.service.product;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.brand.*;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductBrandDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 商品品牌 Service 接口
 *
 * @author aquan
 */
public interface MallProductBrandService {

    /**
     * 创建商品品牌
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductBrand(@Valid MallProductBrandCreateReqVO createReqVO);

    /**
     * 更新商品品牌
     *
     * @param updateReqVO 更新信息
     */
    void updateProductBrand(@Valid MallProductBrandUpdateReqVO updateReqVO);

    /**
     * 更新商品品牌状态
     *
     * @param updateReqVO 更新信息
     */
    void updateProductBrandStatus(MallProductBrandUpdateStatusReqVO updateReqVO);

    /**
     * 删除商品品牌
     *
     * @param id 编号
     */
    void deleteProductBrand(Long id);

    /**
     * 获得商品品牌
     *
     * @param id 编号
     * @return 商品品牌
     */
    MallProductBrandDO getProductBrand(Long id);

    /**
     * 获得商品品牌列表
     *
     * @param ids 编号
     * @return 商品品牌列表
     */
    List<MallProductBrandDO> getProductBrandList(Collection<Long> ids);

    /**
     * 获得商品品牌分页
     *
     * @param pageReqVO 分页查询
     * @return 商品品牌分页
     */
    PageResult<MallProductBrandDO> getProductBrandPage(MallProductBrandPageReqVO pageReqVO);

    /**
     * 获得商品品牌列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 商品品牌列表
     */
    List<MallProductBrandDO> getProductBrandList(MallProductBrandExportReqVO exportReqVO);


}
