package cn.iocoder.yudao.adminserver.modules.mall.service.product;

import cn.hutool.core.util.RandomUtil;
import cn.iocoder.yudao.adminserver.BaseDbUnitTest;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.brand.MallProductBrandCreateReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.brand.MallProductBrandExportReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.brand.MallProductBrandPageReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.brand.MallProductBrandUpdateReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductBrandDO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.mysql.product.MallProductBrandMapper;
import cn.iocoder.yudao.adminserver.modules.mall.service.product.impl.MallProductBrandServiceImpl;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.adminserver.modules.mall.enums.MallErrorCodeConstants.PRODUCT_BRAND_NOT_EXISTS;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.cloneIgnoreId;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertPojoEquals;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertServiceException;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link MallProductBrandServiceImpl} 的单元测试类
 *
 * @author aquan
 */
@Import(MallProductBrandServiceImpl.class)
public class MallProductBrandServiceTest extends BaseDbUnitTest {

    @Resource
    private MallProductBrandServiceImpl productBrandService;

    @Resource
    private MallProductBrandMapper productBrandMapper;

    @Test
    public void testCreateProductBrand_success() {
        // 准备参数
        MallProductBrandCreateReqVO reqVO = randomPojo(MallProductBrandCreateReqVO.class, o ->
                o.setStatus(RandomUtil.randomEle(CommonStatusEnum.values()).getStatus()));

        // 调用
        Long productBrandId = productBrandService.createProductBrand(reqVO);
        // 断言
        assertNotNull(productBrandId);
        // 校验记录的属性是否正确
        MallProductBrandDO productBrand = productBrandMapper.selectById(productBrandId);
        assertPojoEquals(reqVO, productBrand);
    }

    @Test
    public void testUpdateProductBrand_success() {
        // mock 数据
        MallProductBrandDO dbProductBrand = randomPojo(MallProductBrandDO.class,
                o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus()));
        productBrandMapper.insert(dbProductBrand);// @Sql: 先插入出一条存在的数据
        // 准备参数
        MallProductBrandUpdateReqVO reqVO = randomPojo(MallProductBrandUpdateReqVO.class, o -> {
            o.setStatus(CommonStatusEnum.DISABLE.getStatus());
            o.setId(dbProductBrand.getId()); // 设置更新的 ID
        });

        // 调用
        productBrandService.updateProductBrand(reqVO);
        // 校验是否更新正确
        MallProductBrandDO productBrand = productBrandMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, productBrand);
    }

    @Test
    public void testUpdateProductBrand_notExists() {
        // 准备参数
        MallProductBrandUpdateReqVO reqVO = randomPojo(MallProductBrandUpdateReqVO.class,
                o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus()));

        // 调用, 并断言异常
        assertServiceException(() -> productBrandService.updateProductBrand(reqVO), PRODUCT_BRAND_NOT_EXISTS);
    }

    @Test
    public void testDeleteProductBrand_success() {
        // mock 数据
        MallProductBrandDO dbProductBrand = randomPojo(MallProductBrandDO.class,
                o -> o.setStatus(CommonStatusEnum.ENABLE.getStatus()));
        productBrandMapper.insert(dbProductBrand);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbProductBrand.getId();

        // 调用
        productBrandService.deleteProductBrand(id);
        // 校验数据不存在了
        assertNull(productBrandMapper.selectById(id));
    }

    @Test
    public void testDeleteProductBrand_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> productBrandService.deleteProductBrand(id), PRODUCT_BRAND_NOT_EXISTS);
    }

    @Test // TODO 请修改 null 为需要的值
    public void testGetProductBrandPage() {
        // mock 数据
        MallProductBrandDO dbProductBrand = randomPojo(MallProductBrandDO.class, o -> { // 等会查询到
            o.setName("奥迪");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        productBrandMapper.insert(dbProductBrand);
        // 测试 name 不匹配
        productBrandMapper.insert(cloneIgnoreId(dbProductBrand, o -> o.setName("奔驰")));
        // 测试 status 不匹配
        productBrandMapper.insert(cloneIgnoreId(dbProductBrand, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // 准备参数
        MallProductBrandPageReqVO reqVO = new MallProductBrandPageReqVO();
        reqVO.setName("奥迪");
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());

        // 调用
        PageResult<MallProductBrandDO> pageResult = productBrandService.getProductBrandPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbProductBrand, pageResult.getList().get(0));
    }

    @Test // TODO 请修改 null 为需要的值
    public void testGetProductBrandList() {
        // mock 数据
        MallProductBrandDO dbProductBrand = randomPojo(MallProductBrandDO.class, o -> { // 等会查询到
            o.setName("奥迪");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
        });
        productBrandMapper.insert(dbProductBrand);
        // 测试 name 不匹配
        productBrandMapper.insert(cloneIgnoreId(dbProductBrand, o -> o.setName("奔驰")));
        // 测试 status 不匹配
        productBrandMapper.insert(cloneIgnoreId(dbProductBrand, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // 准备参数
        MallProductBrandExportReqVO reqVO = new MallProductBrandExportReqVO();
        reqVO.setName("奥迪");
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());

        // 调用
        List<MallProductBrandDO> list = productBrandService.getProductBrandList(reqVO);
        // 断言
        assertEquals(1, list.size());
        assertPojoEquals(dbProductBrand, list.get(0));
    }

}
