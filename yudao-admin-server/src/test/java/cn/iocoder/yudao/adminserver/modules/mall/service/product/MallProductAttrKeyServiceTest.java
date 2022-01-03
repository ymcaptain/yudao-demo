package cn.iocoder.yudao.adminserver.modules.mall.service.product;

import cn.hutool.core.util.RandomUtil;
import cn.iocoder.yudao.adminserver.BaseDbUnitTest;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrkey.MallProductAttrKeyCreateReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrkey.MallProductAttrKeyExportReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrkey.MallProductAttrKeyPageReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrkey.MallProductAttrKeyUpdateReqVO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductAttrKeyDO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.mysql.product.MallProductAttrKeyMapper;
import cn.iocoder.yudao.adminserver.modules.mall.service.product.impl.MallProductAttrKeyServiceImpl;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.adminserver.modules.mall.enums.MallErrorCodeConstants.PRODUCT_ATTR_KEY_NOT_EXISTS;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.buildTime;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.cloneIgnoreId;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertPojoEquals;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.assertServiceException;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomLongId;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.randomPojo;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link MallProductAttrKeyServiceImpl} 的单元测试类
 *
 * @author aquan
 */
@Import(MallProductAttrKeyServiceImpl.class)
public class MallProductAttrKeyServiceTest extends BaseDbUnitTest {

    @Resource
    private MallProductAttrKeyServiceImpl productAttrKeyService;

    @Resource
    private MallProductAttrKeyMapper productAttrKeyMapper;

    @Test
    public void testCreateProductAttrKey_success() {
        // 准备参数
        MallProductAttrKeyCreateReqVO reqVO = randomPojo(MallProductAttrKeyCreateReqVO.class,o ->
        o.setStatus((RandomUtil.randomEle(CommonStatusEnum.values()).getStatus())));

        // 调用
        Long productAttrKeyId = productAttrKeyService.createProductAttrKey(reqVO);
        // 断言
        assertNotNull(productAttrKeyId);
        // 校验记录的属性是否正确
        MallProductAttrKeyDO productAttrKey = productAttrKeyMapper.selectById(productAttrKeyId);
        assertPojoEquals(reqVO, productAttrKey);
    }

    @Test
    public void testUpdateProductAttrKey_success() {
        // mock 数据
        MallProductAttrKeyDO dbProductAttrKey = randomPojo(MallProductAttrKeyDO.class,o ->
                o.setStatus((RandomUtil.randomEle(CommonStatusEnum.values()).getStatus())));
        productAttrKeyMapper.insert(dbProductAttrKey);// @Sql: 先插入出一条存在的数据
        // 准备参数
        MallProductAttrKeyUpdateReqVO reqVO = randomPojo(MallProductAttrKeyUpdateReqVO.class, o -> {
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setId(dbProductAttrKey.getId()); // 设置更新的 ID
        });

        // 调用
        productAttrKeyService.updateProductAttrKey(reqVO);
        // 校验是否更新正确
        MallProductAttrKeyDO productAttrKey = productAttrKeyMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, productAttrKey);
    }

    @Test
    public void testUpdateProductAttrKey_notExists() {
        // 准备参数
        MallProductAttrKeyUpdateReqVO reqVO = randomPojo(MallProductAttrKeyUpdateReqVO.class,o ->
                o.setStatus((RandomUtil.randomEle(CommonStatusEnum.values()).getStatus())));

        // 调用, 并断言异常
        assertServiceException(() -> productAttrKeyService.updateProductAttrKey(reqVO), PRODUCT_ATTR_KEY_NOT_EXISTS);
    }

    @Test
    public void testDeleteProductAttrKey_success() {
        // mock 数据
        MallProductAttrKeyDO dbProductAttrKey = randomPojo(MallProductAttrKeyDO.class,o ->
                o.setStatus((RandomUtil.randomEle(CommonStatusEnum.values()).getStatus())));
        productAttrKeyMapper.insert(dbProductAttrKey);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbProductAttrKey.getId();

        // 调用
        productAttrKeyService.deleteProductAttrKey(id);
        // 校验数据不存在了
        assertNull(productAttrKeyMapper.selectById(id));
    }

    @Test
    public void testDeleteProductAttrKey_notExists() {
        // 准备参数
        // mock 数据
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> productAttrKeyService.deleteProductAttrKey(id), PRODUCT_ATTR_KEY_NOT_EXISTS);
    }

    @Test // TODO 请修改 null 为需要的值
    public void testGetProductAttrKeyPage() {
        // mock 数据
        MallProductAttrKeyDO dbProductAttrKey = randomPojo(MallProductAttrKeyDO.class, o -> { // 等会查询到
            o.setName("电子产品");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setCreateTime(buildTime(2022, 1, 3));
        });
        productAttrKeyMapper.insert(dbProductAttrKey);
        // 测试 name 不匹配
        productAttrKeyMapper.insert(cloneIgnoreId(dbProductAttrKey, o -> o.setName("服装")));
        // 测试 status 不匹配
        productAttrKeyMapper.insert(
                cloneIgnoreId(dbProductAttrKey, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // 测试 createTime 不匹配
        productAttrKeyMapper.insert(cloneIgnoreId(dbProductAttrKey, o -> o.setCreateTime(buildTime(2021, 11, 3))));
        // 准备参数
        MallProductAttrKeyPageReqVO reqVO = new MallProductAttrKeyPageReqVO();
        reqVO.setName("电子产品");
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        reqVO.setBeginCreateTime(buildTime(2022, 1, 3));
        reqVO.setEndCreateTime(buildTime(2022, 1, 3));

        // 调用
        PageResult<MallProductAttrKeyDO> pageResult = productAttrKeyService.getProductAttrKeyPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbProductAttrKey, pageResult.getList().get(0));
    }

    @Test // TODO 请修改 null 为需要的值
    public void testGetProductAttrKeyList() {
        // mock 数据
        MallProductAttrKeyDO dbProductAttrKey = randomPojo(MallProductAttrKeyDO.class, o -> { // 等会查询到
            o.setName("电子产品");
            o.setStatus(CommonStatusEnum.ENABLE.getStatus());
            o.setCreateTime(buildTime(2022, 1, 3));
        });
        productAttrKeyMapper.insert(dbProductAttrKey);
        // 测试 name 不匹配
        productAttrKeyMapper.insert(cloneIgnoreId(dbProductAttrKey, o -> o.setName("服装")));
        // 测试 status 不匹配
        productAttrKeyMapper.insert(
                cloneIgnoreId(dbProductAttrKey, o -> o.setStatus(CommonStatusEnum.DISABLE.getStatus())));
        // 测试 createTime 不匹配
        productAttrKeyMapper.insert(cloneIgnoreId(dbProductAttrKey, o -> o.setCreateTime(buildTime(2021, 11, 3))));
        // 准备参数
        MallProductAttrKeyExportReqVO reqVO = new MallProductAttrKeyExportReqVO();
        reqVO.setName("电子产品");
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        reqVO.setBeginCreateTime(buildTime(2022, 1, 3));
        reqVO.setEndCreateTime(buildTime(2022, 1, 3));

        // 调用
        List<MallProductAttrKeyDO> list = productAttrKeyService.getProductAttrKeyList(reqVO);
        // 断言
        assertEquals(1, list.size());
        assertPojoEquals(dbProductAttrKey, list.get(0));
    }

}
