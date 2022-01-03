package cn.iocoder.yudao.adminserver.modules.mall.service.product.impl;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrkey.*;
import cn.iocoder.yudao.adminserver.modules.mall.convert.product.MallProductAttrKeyConvert;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductAttrKeyDO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.mysql.product.MallProductAttrKeyMapper;
import cn.iocoder.yudao.adminserver.modules.mall.dal.mysql.product.MallProductAttrValueMapper;
import cn.iocoder.yudao.adminserver.modules.mall.service.product.MallProductAttrKeyService;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.adminserver.modules.mall.enums.MallErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 商品规格键 Service 实现类
 *
 * @author aquan
 */
@Service
@Validated
public class MallProductAttrKeyServiceImpl implements MallProductAttrKeyService {

    @Resource
    private MallProductAttrKeyMapper productAttrKeyMapper;

    @Resource
    private MallProductAttrValueMapper productAttrValueMapper;

    @Override
    public Long createProductAttrKey(MallProductAttrKeyCreateReqVO createReqVO) {
        // 插入
        MallProductAttrKeyDO productAttrKey = MallProductAttrKeyConvert.INSTANCE.convert(createReqVO);
        productAttrKeyMapper.insert(productAttrKey);
        // 返回
        return productAttrKey.getId();
    }

    @Override
    public void updateProductAttrKey(MallProductAttrKeyUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateProductAttrKeyExists(updateReqVO.getId());
        // 更新
        MallProductAttrKeyDO updateObj = MallProductAttrKeyConvert.INSTANCE.convert(updateReqVO);
        productAttrKeyMapper.updateById(updateObj);
    }

    /**
     * 更新商品规格键状态
     *
     * @param updateStatusReqVO 更新状态
     */
    @Override
    public void updateProductAttrKeyStatus(MallProductAttrKeyUpdateStatusReqVO updateStatusReqVO) {
        // 校验存在
        this.validateProductAttrKeyExists(updateStatusReqVO.getId());
        productAttrKeyMapper.updateById(MallProductAttrKeyConvert.INSTANCE.convert(updateStatusReqVO));
    }

    @Override
    public void deleteProductAttrKey(Long id) {
        // 校验存在
        this.validateProductAttrKeyExists(id);

        // 校验如果商品规格键下有值则无法删除
        if (productAttrValueMapper.selectCount(id) > 0) {
            throw exception(PRODUCT_ATTR_KEY_EXIST_VALUES_CANT_DELETE);
        }

        // 删除
        productAttrKeyMapper.deleteById(id);
    }

    private void validateProductAttrKeyExists(Long id) {
        if (productAttrKeyMapper.selectById(id) == null) {
            throw exception(PRODUCT_ATTR_KEY_NOT_EXISTS);
        }
    }

    @Override
    public MallProductAttrKeyDO getProductAttrKey(Long id) {
        return productAttrKeyMapper.selectById(id);
    }

    @Override
    public List<MallProductAttrKeyDO> getProductAttrKeyList(Collection<Long> ids) {
        return productAttrKeyMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<MallProductAttrKeyDO> getProductAttrKeyPage(MallProductAttrKeyPageReqVO pageReqVO) {
        return productAttrKeyMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MallProductAttrKeyDO> getProductAttrKeyList(MallProductAttrKeyExportReqVO exportReqVO) {
        return productAttrKeyMapper.selectList(exportReqVO);
    }


    @Override
    public List<MallProductAttrKeyDO> getProductAttrKeyList() {
        return productAttrKeyMapper.selectList();
    }
}
