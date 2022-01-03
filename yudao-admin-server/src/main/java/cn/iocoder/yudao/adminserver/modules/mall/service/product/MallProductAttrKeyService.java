package cn.iocoder.yudao.adminserver.modules.mall.service.product;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrkey.*;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductAttrKeyDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;

import javax.validation.Valid;
import java.util.*;

/**
 * 商品规格键 Service 接口
 *
 * @author aquan
 */
public interface MallProductAttrKeyService {

    /**
     * 创建商品规格键
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductAttrKey(@Valid MallProductAttrKeyCreateReqVO createReqVO);

    /**
     * 更新商品规格键
     *
     * @param updateReqVO 更新信息
     */
    void updateProductAttrKey(@Valid MallProductAttrKeyUpdateReqVO updateReqVO);

    /**
     * 更新商品规格键状态
     *
     * @param updateStatusReqVO 更新状态
     */
    void updateProductAttrKeyStatus(@Valid MallProductAttrKeyUpdateStatusReqVO updateStatusReqVO);

    /**
     * 删除商品规格键
     *
     * @param id 编号
     */
    void deleteProductAttrKey(Long id);

    /**
     * 获得商品规格键
     *
     * @param id 编号
     * @return 商品规格键
     */
    MallProductAttrKeyDO getProductAttrKey(Long id);

    /**
     * 获得商品规格键列表
     *
     * @param ids 编号
     * @return 商品规格键列表
     */
    List<MallProductAttrKeyDO> getProductAttrKeyList(Collection<Long> ids);

    /**
     * 获得商品规格键分页
     *
     * @param pageReqVO 分页查询
     * @return 商品规格键分页
     */
    PageResult<MallProductAttrKeyDO> getProductAttrKeyPage(MallProductAttrKeyPageReqVO pageReqVO);

    /**
     * 获得商品规格键列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 商品规格键列表
     */
    List<MallProductAttrKeyDO> getProductAttrKeyList(MallProductAttrKeyExportReqVO exportReqVO);

    /**
     * 获得商品规格键列表
     *
     * @return 商品规格键列表
     */
    List<MallProductAttrKeyDO> getProductAttrKeyList();

    /**
     * 根据 商品规格件 ID 集合 获取 Map 数据列表
     *
     * @param keyIdList 商品规格件 ID 集合
     * @return Map 数据列表
     */
    default Map<Long, MallProductAttrKeyDO> getProductAttrKeyMap(Collection<Long> keyIdList) {
        List<MallProductAttrKeyDO> list = this.getProductAttrKeyList(keyIdList);
        if (list.size() < 1) {
            return Collections.emptyMap();
        }
        return CollectionUtils.convertMap(list, MallProductAttrKeyDO::getId);
    }
}
