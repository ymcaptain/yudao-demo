package cn.iocoder.yudao.adminserver.modules.mall.service.product.impl;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.brand.*;
import cn.iocoder.yudao.adminserver.modules.mall.convert.product.MallProductBrandConvert;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductBrandDO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.mysql.product.MallProductBrandMapper;
import cn.iocoder.yudao.adminserver.modules.mall.service.product.MallProductBrandService;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.adminserver.modules.mall.enums.MallErrorCodeConstants.PRODUCT_BRAND_NOT_EXISTS;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 商品品牌 Service 实现类
 *
 * @author aquan
 */
@Service
@Validated
public class MallProductBrandServiceImpl implements MallProductBrandService {

    @Resource
    private MallProductBrandMapper productBrandMapper;

    @Override
    public Long createProductBrand(MallProductBrandCreateReqVO createReqVO) {
        // 插入
        MallProductBrandDO productBrand = MallProductBrandConvert.INSTANCE.convert(createReqVO);
        productBrandMapper.insert(productBrand);
        // 返回
        return productBrand.getId();
    }

    @Override
    public void updateProductBrand(MallProductBrandUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateProductBrandExists(updateReqVO.getId());
        // 更新
        MallProductBrandDO updateObj = MallProductBrandConvert.INSTANCE.convert(updateReqVO);
        productBrandMapper.updateById(updateObj);
    }

    /**
     * 更新商品品牌状态
     *
     * @param updateReqVO 更新信息
     */
    @Override
    public void updateProductBrandStatus(MallProductBrandUpdateStatusReqVO updateReqVO) {
        // 校验存在
        this.validateProductBrandExists(updateReqVO.getId());
        // 更新
        MallProductBrandDO updateObj = MallProductBrandConvert.INSTANCE.convert(updateReqVO);
        productBrandMapper.updateById(updateObj);
    }

    @Override
    public void deleteProductBrand(Long id) {
        // 校验存在
        this.validateProductBrandExists(id);
        // TODO @aquan 后续优化品牌有商品时，无法删除
        // 删除
        productBrandMapper.deleteById(id);
    }

    private void validateProductBrandExists(Long id) {
        if (productBrandMapper.selectById(id) == null) {
            throw exception(PRODUCT_BRAND_NOT_EXISTS);
        }
    }

    @Override
    public MallProductBrandDO getProductBrand(Long id) {
        return productBrandMapper.selectById(id);
    }

    @Override
    public List<MallProductBrandDO> getProductBrandList(Collection<Long> ids) {
        return productBrandMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<MallProductBrandDO> getProductBrandPage(MallProductBrandPageReqVO pageReqVO) {
        return productBrandMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MallProductBrandDO> getProductBrandList(MallProductBrandExportReqVO exportReqVO) {
        return productBrandMapper.selectList(exportReqVO);
    }

}
