package cn.iocoder.yudao.adminserver.modules.mall.controller.product;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrvalue.*;
import cn.iocoder.yudao.adminserver.modules.mall.convert.product.MallProductAttrValueConvert;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductAttrKeyDO;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductAttrValueDO;
import cn.iocoder.yudao.adminserver.modules.mall.service.product.MallProductAttrKeyService;
import cn.iocoder.yudao.adminserver.modules.mall.service.product.MallProductAttrValueService;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

/**
 * @author aquan
 */
@Api(tags = "商品规格值")
@RestController
@RequestMapping("/mall/product-attr-value")
@Validated
public class MallProductAttrValueController {

    @Resource
    private MallProductAttrValueService productAttrValueService;

    @Resource
    private MallProductAttrKeyService productAttrKeyService;

    @PostMapping("/create")
    @ApiOperation("创建商品规格值")
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:create')")
    public CommonResult<Long> createProductAttrValue(@Valid @RequestBody MallProductAttrValueCreateReqVO createReqVO) {
        return success(productAttrValueService.createProductAttrValue(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新商品规格值")
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:update')")
    public CommonResult<Boolean> updateProductAttrValue(
            @Valid @RequestBody MallProductAttrValueUpdateReqVO updateReqVO) {
        productAttrValueService.updateProductAttrValue(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改商品规格值状态")
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:update')")
    public CommonResult<Boolean> updateProductAttrStatus(
            @Valid @RequestBody MallProductAttrValueUpdateStatusReqVO reqVO) {
        productAttrValueService.updateProductAttrKeyStatus(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除商品规格值")
    @ApiImplicitParam(name = "id", value = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:delete')")
    public CommonResult<Boolean> deleteProductAttrValue(@RequestParam("id") Long id) {
        productAttrValueService.deleteProductAttrValue(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得商品规格值")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:query')")
    public CommonResult<MallProductAttrValueRespVO> getProductAttrValue(@RequestParam("id") Long id) {
        MallProductAttrValueDO productAttrValue = productAttrValueService.getProductAttrValue(id);
        return success(MallProductAttrValueConvert.INSTANCE.convert(productAttrValue));
    }

    @GetMapping("/list")
    @ApiOperation("获得商品规格值列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:query')")
    public CommonResult<List<MallProductAttrValueRespVO>> getProductAttrValueList(
            @RequestParam("ids") Collection<Long> ids) {
        List<MallProductAttrValueDO> list = productAttrValueService.getProductAttrValueList(ids);
        return success(MallProductAttrValueConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得商品规格值分页")
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:query')")
    public CommonResult<PageResult<MallProductAttrValueRespVO>> getProductAttrValuePage(
            @Valid MallProductAttrValuePageReqVO pageVO) {
        PageResult<MallProductAttrValueDO> pageResult = productAttrValueService.getProductAttrValuePage(pageVO);
        return success(MallProductAttrValueConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出商品规格值 Excel")
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:export')")
    @OperateLog(type = EXPORT)
    public void exportProductAttrValueExcel(@Valid MallProductAttrValueExportReqVO exportReqVO,
            HttpServletResponse response) throws IOException {
        // 数据处理
        List<MallProductAttrValueDO> list = productAttrValueService.getProductAttrValueList(exportReqVO);
        List<MallProductAttrValueExcelVO> datas = MallProductAttrValueConvert.INSTANCE.convertList02(list);
        Map<Long, MallProductAttrKeyDO> productAttrKeyMap = productAttrKeyService
                .getProductAttrKeyMap(CollectionUtils.convertList(list, MallProductAttrValueDO::getAttrKeyId));
        datas.forEach(c -> {
            c.setAttrKeyName(productAttrKeyMap.get(c.getAttrKeyId()).getName());
        });

        ExcelUtils.write(response, "商品规格值.xls", "数据", MallProductAttrValueExcelVO.class, datas);
    }

}
