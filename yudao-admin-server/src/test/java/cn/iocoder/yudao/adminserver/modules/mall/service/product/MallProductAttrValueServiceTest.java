package cn.iocoder.yudao.adminserver.modules.mall.service.product;

import cn.hutool.core.util.RandomUtil;
import cn.iocoder.yudao.adminserver.BaseDbUnitTest;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrvalue.MallProductAttrValueCreateReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrvalue.MallProductAttrValueExportReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrvalue.MallProductAttrValuePageReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrvalue.MallProductAttrValueUpdateReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductAttrValueDO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.mysql.product.MallProductAttrValueMapper;
import cn.iocoder.yudao.adminserver.modules.mall.service.product.impl.MallProductAttrValueServiceImpl;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.adminserver.modules.mall.enums.MallErrorCodeConstants.PRODUCT_ATTR_VALUE_NOT_EXISTS;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.buildTime;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.cloneIgnoreId;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertPojoEquals;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertServiceException;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomLongId;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomPojo;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link MallProductAttrValueServiceImpl} 的单元测试类
 *
 * @author aquan
 */
@Import(MallProductAttrValueServiceImpl.class)
public class MallProductAttrValueServiceTest extends BaseDbUnitTest {

    @Resource
    private MallProductAttrValueServiceImpl productAttrValueService;

    @Resource
    private MallProductAttrValueMapper productAttrValueMapper;

    @Test
    public void testCreateProductAttrValue_success() {
        // 准备参数
        MallProductAttrValueCreateReqVO reqVO = randomPojo(MallProductAttrValueCreateReqVO.class, o ->
                o.setStatus((RandomUtil.randomEle(CommonStatusEnum.values()).getStatus())));

        // 调用
        Long productAttrValueId = productAttrValueService.createProductAttrValue(reqVO);
        // 断言
        assertNotNull(productAttrValueId);
        // 校验记录的属性是否正确
        MallProductAttrValueDO productAttrValue = productAttrValueMapper.selectById(productAttrValueId);
        assertPojoEquals(reqVO, productAttrValue);
    }

    @Test
    public void testUpdateProductAttrValue_success() {
        // mock 数据
        MallProductAttrValueDO dbProductAttrValue = randomPojo(MallProductAttrValueDO.class, o ->
                o.setStatus((RandomUtil.randomEle(CommonStatusEnum.values()).getStatus())));
        productAttrValueMapper.insert(dbProductAttrValue);// @Sql: 先插入出一条存在的数据
        // 准备参数
        MallProductAttrValueUpdateReqVO reqVO = randomPojo(MallProductAttrValueUpdateReqVO.class, o -> {
            o.setStatus(CommonStatusEnum.DISABLE.getStatus());
            o.setId(dbProductAttrValue.getId()); // 设置更新的 ID
        });

        // 调用
        productAttrValueService.updateProductAttrValue(reqVO);
        // 校验是否更新正确
        MallProductAttrValueDO productAttrValue = productAttrValueMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, productAttrValue);
    }

    @Test
    public void testUpdateProductAttrValue_notExists() {
        // 准备参数
        MallProductAttrValueUpdateReqVO reqVO = randomPojo(MallProductAttrValueUpdateReqVO.class, o ->
                o.setStatus((RandomUtil.randomEle(CommonStatusEnum.values()).getStatus())));

        // 调用, 并断言异常
        assertServiceException(() -> productAttrValueService.updateProductAttrValue(reqVO),
                PRODUCT_ATTR_VALUE_NOT_EXISTS);
    }

    @Test
    public void testDeleteProductAttrValue_success() {
        // mock 数据
        MallProductAttrValueDO dbProductAttrValue = randomPojo(MallProductAttrValueDO.class, o ->
                o.setStatus((RandomUtil.randomEle(CommonStatusEnum.values()).getStatus())));
        productAttrValueMapper.insert(dbProductAttrValue);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbProductAttrValue.getId();

        // 调用
        productAttrValueService.deleteProductAttrValue(id);
        // 校验数据不存在了
        assertNull(productAttrValueMapper.selectById(id));
    }

    @Test
    public void testDeleteProductAttrValue_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> productAttrValueService.deleteProductAttrValue(id), PRODUCT_ATTR_VALUE_NOT_EXISTS);
    }

    @Test // TODO 请修改 null 为需要的值
    public void testGetProductAttrValuePage() {
        // mock 数据
        MallProductAttrValueDO dbProductAttrValue = randomPojo(MallProductAttrValueDO.class, o -> { // 等会查询到
            o.setAttrKeyId(1L);
            o.setName("海尔笔记本电脑");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setCreateTime(buildTime(2022, 1, 3));
        });
        productAttrValueMapper.insert(dbProductAttrValue);
        // 测试 attrKeyId 不匹配
        productAttrValueMapper.insert(cloneIgnoreId(dbProductAttrValue, o -> o.setAttrKeyId(2L)));
        // 测试 name 不匹配
        productAttrValueMapper.insert(cloneIgnoreId(dbProductAttrValue, o -> o.setName("红双喜乒乓球")));
        // 测试 status 不匹配
        productAttrValueMapper.insert(
                cloneIgnoreId(dbProductAttrValue, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // 测试 createTime 不匹配
        productAttrValueMapper.insert(cloneIgnoreId(dbProductAttrValue, o ->
                o.setCreateTime(buildTime(2021, 12, 12))));
        // 准备参数
        MallProductAttrValuePageReqVO reqVO = new MallProductAttrValuePageReqVO();
        reqVO.setAttrKeyId(1L);
        reqVO.setName("海尔笔记本电脑");
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        reqVO.setBeginCreateTime(buildTime(2022, 1, 3));
        reqVO.setEndCreateTime(buildTime(2022, 1, 3));

        // 调用
        PageResult<MallProductAttrValueDO> pageResult = productAttrValueService.getProductAttrValuePage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbProductAttrValue, pageResult.getList().get(0));
    }

    @Test // TODO 请修改 null 为需要的值
    public void testGetProductAttrValueList() {
        // mock 数据
        MallProductAttrValueDO dbProductAttrValue = randomPojo(MallProductAttrValueDO.class, o -> { // 等会查询到
            o.setAttrKeyId(1L);
            o.setName("海尔笔记本电脑");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setCreateTime(buildTime(2022, 1, 3));
        });
        productAttrValueMapper.insert(dbProductAttrValue);
        // 测试 attrKeyId 不匹配
        productAttrValueMapper.insert(cloneIgnoreId(dbProductAttrValue, o -> o.setAttrKeyId(2L)));
        // 测试 name 不匹配
        productAttrValueMapper.insert(cloneIgnoreId(dbProductAttrValue, o -> o.setName("红双喜乒乓球")));
        // 测试 status 不匹配
        productAttrValueMapper.insert(
                cloneIgnoreId(dbProductAttrValue, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // 测试 createTime 不匹配
        productAttrValueMapper.insert(cloneIgnoreId(dbProductAttrValue, o ->
                o.setCreateTime(buildTime(2021, 12, 12))));
        // 准备参数
        MallProductAttrValueExportReqVO reqVO = new MallProductAttrValueExportReqVO();
        reqVO.setAttrKeyId(1L);
        reqVO.setName("海尔笔记本电脑");
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        reqVO.setBeginCreateTime(buildTime(2022, 1, 3));
        reqVO.setEndCreateTime(buildTime(2022, 1, 3));

        // 调用
        List<MallProductAttrValueDO> list = productAttrValueService.getProductAttrValueList(reqVO);
        // 断言
        assertEquals(1, list.size());
        assertPojoEquals(dbProductAttrValue, list.get(0));
    }

}
