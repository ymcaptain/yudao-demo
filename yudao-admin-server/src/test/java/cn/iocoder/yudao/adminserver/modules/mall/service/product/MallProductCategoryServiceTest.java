package cn.iocoder.yudao.adminserver.modules.mall.service.product;

import cn.iocoder.yudao.adminserver.BaseDbUnitTest;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category.MallProductCategoryCreateReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category.MallProductCategoryExportReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category.MallProductCategoryPageReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category.MallProductCategoryUpdateReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductCategoryDO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.mysql.product.MallProductCategoryMapper;
import cn.iocoder.yudao.adminserver.modules.mall.service.product.impl.MallProductCategoryServiceImpl;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.adminserver.modules.mall.enums.MallErrorCodeConstants.PRODUCT_CATEGORY_NOT_EXISTS;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.buildTime;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.cloneIgnoreId;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertPojoEquals;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertServiceException;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomLongId;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomPojo;
import static org.junit.jupiter.api.Assertions.*;

/**
* {@link MallProductCategoryServiceImpl} 的单元测试类
*
* @author aquan
*/
@Import(MallProductCategoryServiceImpl.class)
public class MallProductCategoryServiceTest extends BaseDbUnitTest {

    @Resource
    private MallProductCategoryServiceImpl productCategoryService;

    @Resource
    private MallProductCategoryMapper productCategoryMapper;

    @Test
    public void testCreateProductCategory_success() {
        // 准备参数
        MallProductCategoryCreateReqVO reqVO = randomPojo(MallProductCategoryCreateReqVO.class,
                o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus()));

        // 调用
        Long productCategoryId = productCategoryService.createProductCategory(reqVO);
        // 断言
        assertNotNull(productCategoryId);
        // 校验记录的属性是否正确
        MallProductCategoryDO productCategory = productCategoryMapper.selectById(productCategoryId);
        assertPojoEquals(reqVO, productCategory);
    }

    @Test
    public void testUpdateProductCategory_success() {
        // mock 数据
        MallProductCategoryDO dbProductCategory = randomPojo(MallProductCategoryDO.class,
                o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus()));
        productCategoryMapper.insert(dbProductCategory);// @Sql: 先插入出一条存在的数据
        // 准备参数
        MallProductCategoryUpdateReqVO reqVO = randomPojo(MallProductCategoryUpdateReqVO.class, o -> {
            o.setStatus(CommonStatusEnum.DISABLE.getStatus());
            o.setId(dbProductCategory.getId()); // 设置更新的 ID
        });

        // 调用
        productCategoryService.updateProductCategory(reqVO);
        // 校验是否更新正确
        MallProductCategoryDO productCategory = productCategoryMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, productCategory);
    }

    @Test
    public void testUpdateProductCategory_notExists() {
        // 准备参数
        MallProductCategoryUpdateReqVO reqVO = randomPojo(MallProductCategoryUpdateReqVO.class,
                o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus()));

        // 调用, 并断言异常
        assertServiceException(() -> productCategoryService.updateProductCategory(reqVO), PRODUCT_CATEGORY_NOT_EXISTS);
    }

    @Test
    public void testDeleteProductCategory_success() {
        // mock 数据
        MallProductCategoryDO dbProductCategory = randomPojo(MallProductCategoryDO.class,
                o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus()));
        productCategoryMapper.insert(dbProductCategory);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbProductCategory.getId();

        // 调用
        productCategoryService.deleteProductCategory(id);
       // 校验数据不存在了
       assertNull(productCategoryMapper.selectById(id));
    }

    @Test
    public void testDeleteProductCategory_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> productCategoryService.deleteProductCategory(id), PRODUCT_CATEGORY_NOT_EXISTS);
    }

    @Test
    public void testGetProductCategoryPage() {
       // mock 数据
       MallProductCategoryDO dbProductCategory = randomPojo(MallProductCategoryDO.class, o -> { // 等会查询到
           o.setPid(0L);
           o.setName("彩电");
           o.setDescription("家庭电器");
           o.setPicUrl("https://127.0.0.1/file/111.jpg");
           o.setSort(0);
           o.setStatus(CommonStatusEnum.ENABLE.getStatus());
           o.setCreateTime(buildTime(2022, 1, 3));
       });
       productCategoryMapper.insert(dbProductCategory);
       // 测试 pid 不匹配
       productCategoryMapper.insert(cloneIgnoreId(dbProductCategory, o -> o.setPid(2L)));
       // 测试 name 不匹配
       productCategoryMapper.insert(cloneIgnoreId(dbProductCategory, o -> o.setName("服装")));
       // 测试 status 不匹配
       productCategoryMapper.insert(cloneIgnoreId(dbProductCategory, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
       // 准备参数
       MallProductCategoryPageReqVO reqVO = new MallProductCategoryPageReqVO();
       reqVO.setPid(0L);
       reqVO.setName("彩电");
       reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());

       // 调用
       PageResult<MallProductCategoryDO> pageResult = productCategoryService.getProductCategoryPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbProductCategory, pageResult.getList().get(0));
    }

    @Test
    public void testGetProductCategoryList() {
       // mock 数据
       MallProductCategoryDO dbProductCategory = randomPojo(MallProductCategoryDO.class, o -> { // 等会查询到
           o.setPid(0L);
           o.setName("彩电");
           o.setDescription("家庭电器");
           o.setPicUrl("https://127.0.0.1/file/111.jpg");
           o.setSort(0);
           o.setStatus(CommonStatusEnum.ENABLE.getStatus());
           o.setCreateTime(buildTime(2022, 1, 3));
       });
        productCategoryMapper.insert(dbProductCategory);
        // 测试 pid 不匹配
        productCategoryMapper.insert(cloneIgnoreId(dbProductCategory, o -> o.setPid(2L)));
        // 测试 name 不匹配
        productCategoryMapper.insert(cloneIgnoreId(dbProductCategory, o -> o.setName("服装")));
        // 测试 status 不匹配
        productCategoryMapper.insert(cloneIgnoreId(dbProductCategory, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
       // 准备参数
       MallProductCategoryExportReqVO reqVO = new MallProductCategoryExportReqVO();
        reqVO.setPid(0L);
        reqVO.setName("彩电");
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());

       // 调用
       List<MallProductCategoryDO> list = productCategoryService.getProductCategoryList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbProductCategory, list.get(0));
    }

}
