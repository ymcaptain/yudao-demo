package cn.iocoder.yudao.adminserver.modules.mall.service.product.impl;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrvalue.*;
import cn.iocoder.yudao.adminserver.modules.mall.convert.product.MallProductAttrValueConvert;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductAttrValueDO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.mysql.product.MallProductAttrValueMapper;
import cn.iocoder.yudao.adminserver.modules.mall.service.product.MallProductAttrValueService;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.adminserver.modules.mall.enums.MallErrorCodeConstants.PRODUCT_ATTR_VALUE_NOT_EXISTS;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 商品规格值 Service 实现类
 *
 * @author aquan
 */
@Service
@Validated
public class MallProductAttrValueServiceImpl implements MallProductAttrValueService {

    @Resource
    private MallProductAttrValueMapper productAttrValueMapper;

    @Override
    public Long createProductAttrValue(MallProductAttrValueCreateReqVO createReqVO) {
        // 插入
        MallProductAttrValueDO productAttrValue = MallProductAttrValueConvert.INSTANCE.convert(createReqVO);
        productAttrValueMapper.insert(productAttrValue);
        // 返回
        return productAttrValue.getId();
    }

    @Override
    public void updateProductAttrValue(MallProductAttrValueUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateProductAttrValueExists(updateReqVO.getId());
        // 更新
        MallProductAttrValueDO updateObj = MallProductAttrValueConvert.INSTANCE.convert(updateReqVO);
        productAttrValueMapper.updateById(updateObj);
    }

    /**
     * 更新商品规格值状态
     *
     * @param updateStatusReqVO 更新状态
     */
    @Override
    public void updateProductAttrKeyStatus(MallProductAttrValueUpdateStatusReqVO updateStatusReqVO) {
        this.validateProductAttrValueExists(updateStatusReqVO.getId());
        productAttrValueMapper.updateById(MallProductAttrValueConvert.INSTANCE.convert(updateStatusReqVO));
    }

    @Override
    public void deleteProductAttrValue(Long id) {
        // 校验存在
        this.validateProductAttrValueExists(id);
        // todo @aquan 后续优化有商品使用当前值的时候无法删除
        // 删除
        productAttrValueMapper.deleteById(id);
    }

    private void validateProductAttrValueExists(Long id) {
        if (productAttrValueMapper.selectById(id) == null) {
            throw exception(PRODUCT_ATTR_VALUE_NOT_EXISTS);
        }
    }

    @Override
    public MallProductAttrValueDO getProductAttrValue(Long id) {
        return productAttrValueMapper.selectById(id);
    }

    @Override
    public List<MallProductAttrValueDO> getProductAttrValueList(Collection<Long> ids) {
        return productAttrValueMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<MallProductAttrValueDO> getProductAttrValuePage(MallProductAttrValuePageReqVO pageReqVO) {
        return productAttrValueMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MallProductAttrValueDO> getProductAttrValueList(MallProductAttrValueExportReqVO exportReqVO) {
        return productAttrValueMapper.selectList(exportReqVO);
    }

}
