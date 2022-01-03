package cn.iocoder.yudao.adminserver.modules.mall.service.product;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrvalue.*;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductAttrValueDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 商品规格值 Service 接口
 *
 * @author aquan
 */
public interface MallProductAttrValueService {

    /**
     * 创建商品规格值
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductAttrValue(@Valid MallProductAttrValueCreateReqVO createReqVO);

    /**
     * 更新商品规格值
     *
     * @param updateReqVO 更新信息
     */
    void updateProductAttrValue(@Valid MallProductAttrValueUpdateReqVO updateReqVO);

    /**
     * 更新商品规格值状态
     *
     * @param updateStatusReqVO 更新状态
     */
    void updateProductAttrKeyStatus(@Valid MallProductAttrValueUpdateStatusReqVO updateStatusReqVO);

    /**
     * 删除商品规格值
     *
     * @param id 编号
     */
    void deleteProductAttrValue(Long id);

    /**
     * 获得商品规格值
     *
     * @param id 编号
     * @return 商品规格值
     */
    MallProductAttrValueDO getProductAttrValue(Long id);

    /**
     * 获得商品规格值列表
     *
     * @param ids 编号
     * @return 商品规格值列表
     */
    List<MallProductAttrValueDO> getProductAttrValueList(Collection<Long> ids);

    /**
     * 获得商品规格值分页
     *
     * @param pageReqVO 分页查询
     * @return 商品规格值分页
     */
    PageResult<MallProductAttrValueDO> getProductAttrValuePage(MallProductAttrValuePageReqVO pageReqVO);

    /**
     * 获得商品规格值列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 商品规格值列表
     */
    List<MallProductAttrValueDO> getProductAttrValueList(MallProductAttrValueExportReqVO exportReqVO);


}
