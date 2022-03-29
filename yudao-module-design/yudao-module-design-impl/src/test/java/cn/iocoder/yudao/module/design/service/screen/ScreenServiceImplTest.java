package cn.iocoder.yudao.module.design.service.screen;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.module.design.test.BaseDbUnitTest;
import cn.iocoder.yudao.module.design.controller.admin.screen.vo.*;
import cn.iocoder.yudao.module.design.dal.dataobject.screen.ScreenDO;
import cn.iocoder.yudao.module.design.dal.mysql.screen.ScreenMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.design.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
* {@link ScreenServiceImpl} 的单元测试类
*
* @author Timeless小帅
*/
@Import(ScreenServiceImpl.class)
public class ScreenServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ScreenServiceImpl screenService;

    @Resource
    private ScreenMapper screenMapper;

    @Test
    public void testCreateScreen_success() {
        // 准备参数
        ScreenCreateReqVO reqVO = randomPojo(ScreenCreateReqVO.class);

        // 调用
        Long screenId = screenService.createScreen(reqVO);
        // 断言
        assertNotNull(screenId);
        // 校验记录的属性是否正确
        ScreenDO screen = screenMapper.selectById(screenId);
        assertPojoEquals(reqVO, screen);
    }

    @Test
    public void testUpdateScreen_success() {
        // mock 数据
        ScreenDO dbScreen = randomPojo(ScreenDO.class);
        screenMapper.insert(dbScreen);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ScreenUpdateReqVO reqVO = randomPojo(ScreenUpdateReqVO.class, o -> {
            o.setId(dbScreen.getId()); // 设置更新的 ID
        });

        // 调用
        screenService.updateScreen(reqVO);
        // 校验是否更新正确
        ScreenDO screen = screenMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, screen);
    }

    @Test
    public void testUpdateScreen_notExists() {
        // 准备参数
        ScreenUpdateReqVO reqVO = randomPojo(ScreenUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> screenService.updateScreen(reqVO), SCREEN_NOT_EXISTS);
    }

    @Test
    public void testDeleteScreen_success() {
        // mock 数据
        ScreenDO dbScreen = randomPojo(ScreenDO.class);
        screenMapper.insert(dbScreen);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbScreen.getId();

        // 调用
        screenService.deleteScreen(id);
       // 校验数据不存在了
       assertNull(screenMapper.selectById(id));
    }

    @Test
    public void testDeleteScreen_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> screenService.deleteScreen(id), SCREEN_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetScreenPage() {
       // mock 数据
       ScreenDO dbScreen = randomPojo(ScreenDO.class, o -> { // 等会查询到
           o.setTitle(null);
           o.setScaleX(null);
           o.setScaleY(null);
           o.setViewCode(null);
           o.setCountView(null);
           o.setCreateTime(null);
       });
       screenMapper.insert(dbScreen);
       // 测试 title 不匹配
       screenMapper.insert(cloneIgnoreId(dbScreen, o -> o.setTitle(null)));
       // 测试 scaleX 不匹配
       screenMapper.insert(cloneIgnoreId(dbScreen, o -> o.setScaleX(null)));
       // 测试 scaleY 不匹配
       screenMapper.insert(cloneIgnoreId(dbScreen, o -> o.setScaleY(null)));
       // 测试 viewCode 不匹配
       screenMapper.insert(cloneIgnoreId(dbScreen, o -> o.setViewCode(null)));
       // 测试 countView 不匹配
       screenMapper.insert(cloneIgnoreId(dbScreen, o -> o.setCountView(null)));
       // 测试 createTime 不匹配
       screenMapper.insert(cloneIgnoreId(dbScreen, o -> o.setCreateTime(null)));
       // 准备参数
       ScreenPageReqVO reqVO = new ScreenPageReqVO();
       reqVO.setTitle(null);
       reqVO.setScaleX(null);
       reqVO.setScaleY(null);
       reqVO.setDesignImgId(null);
       reqVO.setState(null);
       reqVO.setViewCode(null);
       reqVO.setCountView(null);
       reqVO.setBeginCreateTime(null);
       reqVO.setEndCreateTime(null);

       // 调用
       PageResult<ScreenDO> pageResult = screenService.getScreenPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbScreen, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetScreenList() {
       // mock 数据
       ScreenDO dbScreen = randomPojo(ScreenDO.class, o -> { // 等会查询到
           o.setTitle(null);
           o.setScaleX(null);
           o.setScaleY(null);
           o.setViewCode(null);
           o.setCountView(null);
           o.setCreateTime(null);
       });
       screenMapper.insert(dbScreen);
       // 测试 title 不匹配
       screenMapper.insert(cloneIgnoreId(dbScreen, o -> o.setTitle(null)));
       // 测试 scaleX 不匹配
       screenMapper.insert(cloneIgnoreId(dbScreen, o -> o.setScaleX(null)));
       // 测试 scaleY 不匹配
       screenMapper.insert(cloneIgnoreId(dbScreen, o -> o.setScaleY(null)));
       // 测试 viewCode 不匹配
       screenMapper.insert(cloneIgnoreId(dbScreen, o -> o.setViewCode(null)));
       // 测试 countView 不匹配
       screenMapper.insert(cloneIgnoreId(dbScreen, o -> o.setCountView(null)));
       // 测试 createTime 不匹配
       screenMapper.insert(cloneIgnoreId(dbScreen, o -> o.setCreateTime(null)));
       // 准备参数
       ScreenExportReqVO reqVO = new ScreenExportReqVO();
       reqVO.setTitle(null);
       reqVO.setScaleX(null);
       reqVO.setScaleY(null);
       reqVO.setState(null);
       reqVO.setViewCode(null);
       reqVO.setCountView(null);
       reqVO.setBeginCreateTime(null);
       reqVO.setEndCreateTime(null);

       // 调用
       List<ScreenDO> list = screenService.getScreenList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbScreen, list.get(0));
    }

}
