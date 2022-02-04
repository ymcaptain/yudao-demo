package cn.iocoder.yudao.adminserver.modules.mall.service.product.impl;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category.*;
import cn.iocoder.yudao.adminserver.modules.mall.convert.product.MallProductCategoryConvert;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductCategoryDO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.mysql.product.MallProductCategoryMapper;
import cn.iocoder.yudao.adminserver.modules.mall.enums.MallProductCategoryConstants;
import cn.iocoder.yudao.adminserver.modules.mall.service.product.MallProductCategoryService;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.adminserver.modules.mall.enums.MallErrorCodeConstants.PRODUCT_CATEGORY_LEAVERS_EXISTS_CANT_DELETE;
import static cn.iocoder.yudao.adminserver.modules.mall.enums.MallErrorCodeConstants.PRODUCT_CATEGORY_NOT_EXISTS;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 商品分类 Service 实现类
 *
 * @author aquan
 */
@Service
@Validated
public class MallProductCategoryServiceImpl implements MallProductCategoryService {

    @Resource
    private MallProductCategoryMapper productCategoryMapper;

    @Override
    public Long createProductCategory(MallProductCategoryCreateReqVO createReqVO) {
        // 插入
        MallProductCategoryDO productCategory = MallProductCategoryConvert.INSTANCE.convert(createReqVO);
        productCategoryMapper.insert(productCategory);
        // 返回
        return productCategory.getId();
    }

    @Override
    public void updateProductCategory(MallProductCategoryUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateProductCategoryExists(updateReqVO.getId());
        // 更新
        MallProductCategoryDO updateObj = MallProductCategoryConvert.INSTANCE.convert(updateReqVO);
        productCategoryMapper.updateById(updateObj);
    }

    /**
     * 更新商品分类状态
     *
     * @param updateReqVO 更新信息
     */
    @Override
    public void updateProductBrandStatus(MallProductCategoryUpdateStatusReqVO updateReqVO) {
        // 校验存在
        this.validateProductCategoryExists(updateReqVO.getId());
        // 更新
        MallProductCategoryDO updateObj = MallProductCategoryConvert.INSTANCE.convert(updateReqVO);
        productCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteProductCategory(Long id) {
        // 校验存在
        this.validateProductCategoryExists(id);
        // 校验是否存在子类目
        this.validateProductCategoryContainsLeaves(id);
        // 删除
        productCategoryMapper.deleteById(id);
    }

    private void validateProductCategoryExists(Long id) {
        if (productCategoryMapper.selectById(id) == null) {
            throw exception(PRODUCT_CATEGORY_NOT_EXISTS);
        }
    }

    private void validateProductCategoryContainsLeaves(Long id) {
        if (productCategoryMapper.selectLeavesCount(id) > 0) {
            throw exception(PRODUCT_CATEGORY_LEAVERS_EXISTS_CANT_DELETE);
        }
    }

    @Override
    public MallProductCategoryDO getProductCategory(Long id) {
        return productCategoryMapper.selectById(id);
    }

    @Override
    public List<MallProductCategoryDO> getProductCategoryRoots() {
        return productCategoryMapper.selectRoots();
    }

    @Override
    public List<MallProductCategoryDO> getProductCategoryList(Collection<Long> ids) {
        return productCategoryMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<MallProductCategoryDO> getProductCategoryPage(MallProductCategoryPageReqVO pageReqVO) {
        // 对栏父栏目ID做处理，如果是空的则表示为根栏目
        if (pageReqVO.getPid() == null) {
            pageReqVO.setPid(MallProductCategoryConstants.CATEGORY_ROOT_PID);
        }
        return productCategoryMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MallProductCategoryDO> getProductCategoryList(MallProductCategoryExportReqVO exportReqVO) {
        // 对栏父栏目ID做处理，如果是空的则表示为根栏目
        if (exportReqVO.getPid() == null) {
            exportReqVO.setPid(MallProductCategoryConstants.CATEGORY_ROOT_PID);
        }
        return productCategoryMapper.selectList(exportReqVO);
    }

}
