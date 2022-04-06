package cn.iocoder.yudao.module.design.controller.admin.screen;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.design.controller.admin.screen.vo.*;
import cn.iocoder.yudao.module.design.convert.screen.ScreenConvert;
import cn.iocoder.yudao.module.design.dal.dataobject.screen.ScreenDO;
import cn.iocoder.yudao.module.design.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.design.service.screen.ScreenService;
import cn.iocoder.yudao.module.infra.api.select.SelectListApi;
import cn.iocoder.yudao.module.infra.api.select.vo.SelectCommonVo;
import cn.iocoder.yudao.module.infra.api.select.vo.SelectListQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.*;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.infra.api.select.SelectListApi;
import cn.iocoder.yudao.module.infra.api.select.vo.SelectCommonVo;
import cn.iocoder.yudao.module.infra.api.select.vo.SelectListQueryVo;

import cn.iocoder.yudao.module.design.controller.admin.screen.vo.*;
import cn.iocoder.yudao.module.design.dal.dataobject.screen.ScreenDO;
import cn.iocoder.yudao.module.design.convert.screen.ScreenConvert;
import cn.iocoder.yudao.module.design.service.screen.ScreenService;

@Api(tags = "管理后台 - 数据大屏")
@RestController
@RequestMapping("/design/screen")
@Validated
public class ScreenController {

    @Resource
    private SelectListApi selectListApi;

    @Resource
    private ScreenService screenService;

    @PostMapping("/create")
    @ApiOperation("创建数据大屏")
    @PreAuthorize("@ss.hasPermission('design:screen:create')")
    public CommonResult<Long> createScreen(@Valid @RequestBody ScreenCreateReqVO createReqVO) {
        createReqVO.setScaleX(16);
        createReqVO.setScaleY(9);
        createReqVO.setBgColor("#000");
        createReqVO.setCountView(0);
        return success(screenService.createScreen(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新数据大屏")
    @PreAuthorize("@ss.hasPermission('design:screen:update')")
    public CommonResult<Boolean> updateScreen(@Valid @RequestBody ScreenUpdateReqVO updateReqVO) {
        screenService.updateScreen(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除数据大屏")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('design:screen:delete')")
    public CommonResult<Boolean> deleteScreen(@RequestParam("id") Long id) {
        screenService.deleteScreen(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得数据大屏")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('design:screen:query')")
    public CommonResult<ScreenRespVO> getScreen(@RequestParam("id") Long id) {
        ScreenDO screen = screenService.getScreen(id);
        return success(ScreenConvert.INSTANCE.convert(screen));
    }

    @GetMapping("/list")
    @ApiOperation("获得数据大屏列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('design:screen:query')")
    public CommonResult<List<ScreenRespVO>> getScreenList(@RequestParam("ids") Collection<Long> ids) {
        List<ScreenDO> list = screenService.getScreenList(ids);
        return success(ScreenConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得数据大屏分页")
    @PreAuthorize("@ss.hasPermission('design:screen:query')")
    public CommonResult<PageResult<ScreenRespVO>> getScreenPage(@Valid ScreenPageReqVO pageVO) {
        PageResult<ScreenDO> pageResult = screenService.getScreenPage(pageVO);
        return success(ScreenConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出数据大屏 Excel")
    @PreAuthorize("@ss.hasPermission('design:screen:export')")
    @OperateLog(type = EXPORT)
    public void exportScreenExcel(@Valid ScreenExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<ScreenDO> list = screenService.getScreenList(exportReqVO);
        // 导出 Excel
        List<ScreenExcelVO> datas = ScreenConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "数据大屏.xls", "数据", ScreenExcelVO.class, datas);
    }

    @GetMapping("/selectList")
    @ApiOperation("获得数据大屏定义列表")
    @PreAuthorize("@ss.hasPermission('design:screen:query')")
    public CommonResult<List<SelectCommonVo>> getScreenSelectList(SelectListQueryVo queryVO) {
        String name = queryVO.getTableName();
        return success(new ArrayList<SelectCommonVo>());
    }

    // TODO @宋康帅：项目比使用 path 的路径哈，很多中间件兼容性不好
    /**
     *  获取大屏详情
     * @param id    大屏ID
     * @param mode  0编辑，1发布浏览，2预览
     * @param viewCode  访问码
     */
    @GetMapping("/getById/{id}/{mode}/{viewCode}")
    public CommonResult<ScreenUpdateReqVO> getById(@PathVariable("id") Long id, @PathVariable("mode") Integer mode,
                          @PathVariable("viewCode") String viewCode){
        // TODO @宋康帅：是不是获取数据，和增加访问次数，接口分开。
        ScreenDO design = screenService.getScreen(id);
        if (mode == null || mode == 1){//浏览状态
            // TODO 宋康帅：Controller 不要编写业务逻辑哈。countView 可以有个默认 0；然后，增加数量，最好是 db update + 1，不要基于内存计算后加；
            Integer countView = design.getCountView();
            if (countView == null) {
                countView = 1;
            } else {
                countView = countView + 1;
            }
            design.setCountView(countView);
            screenService.updateScreen(ScreenConvert.INSTANCE.convertUpdate(design));

            // TODO @宋康帅：适合抛出业务异常。
            if (StringUtils.isNotBlank(design.getViewCode()) && !viewCode.equals(design.getViewCode())){
                return successMessage("NEED_AUTH");
            }
        }
        return success(ScreenConvert.INSTANCE.convertUpdate(design));
    }

    // TODO @宋康帅：这个接口，和 getById 有一些重复；ScreenUpdateReqVO 是更新请求的 VO 呀
    @GetMapping("/authViewCode")
    public CommonResult<ScreenUpdateReqVO> authViewCode(Long id, String viewCode){
        ScreenDO design = screenService.getScreen(id);
        if (viewCode.equals(design.getViewCode())){
            return success(ScreenConvert.INSTANCE.convertUpdate(design));
        }
        return error(ErrorCodeConstants.SCREEN_CODE_ERROR);
    }


}
