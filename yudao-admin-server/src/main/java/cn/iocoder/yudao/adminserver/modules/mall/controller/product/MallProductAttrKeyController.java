package cn.iocoder.yudao.adminserver.modules.mall.controller.product;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrkey.*;
import cn.iocoder.yudao.adminserver.modules.mall.convert.product.MallProductAttrKeyConvert;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductAttrKeyDO;
import cn.iocoder.yudao.adminserver.modules.mall.service.product.MallProductAttrKeyService;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
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

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

/**
 * @author aquan
 */
@Api(tags = "商品规格键")
@RestController
@RequestMapping("/mall/product-attr-key")
@Validated
public class MallProductAttrKeyController {

    @Resource
    private MallProductAttrKeyService productAttrKeyService;

    @PostMapping("/create")
    @ApiOperation("创建商品规格键")
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:create')")
    public CommonResult<Long> createProductAttrKey(@Valid @RequestBody MallProductAttrKeyCreateReqVO createReqVO) {
        return success(productAttrKeyService.createProductAttrKey(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新商品规格键")
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:update')")
    public CommonResult<Boolean> updateProductAttrKey(@Valid @RequestBody MallProductAttrKeyUpdateReqVO updateReqVO) {
        productAttrKeyService.updateProductAttrKey(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改商品规格键状态")
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:update')")
    public CommonResult<Boolean> updateProductAttrStatus(@Valid @RequestBody MallProductAttrKeyUpdateStatusReqVO reqVO) {
        productAttrKeyService.updateProductAttrKeyStatus(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除商品规格键")
    @ApiImplicitParam(name = "id", value = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:delete')")
    public CommonResult<Boolean> deleteProductAttrKey(@RequestParam("id") Long id) {
        productAttrKeyService.deleteProductAttrKey(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得商品规格键")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:query')")
    public CommonResult<MallProductAttrKeyRespVO> getProductAttrKey(@RequestParam("id") Long id) {
        MallProductAttrKeyDO productAttrKey = productAttrKeyService.getProductAttrKey(id);
        return success(MallProductAttrKeyConvert.INSTANCE.convert(productAttrKey));
    }

    @GetMapping("/list")
    @ApiOperation("获得商品规格键列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:query')")
    public CommonResult<List<MallProductAttrKeyRespVO>> getProductAttrKeyList(
            @RequestParam("ids") Collection<Long> ids) {
        List<MallProductAttrKeyDO> list = productAttrKeyService.getProductAttrKeyList(ids);
        return success(MallProductAttrKeyConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取全部的商品规格键")
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:query')")
    public CommonResult<List<MallProductAttrKeySimpleRespVO>> getAttrKeySimpleList() {
        List<MallProductAttrKeyDO> list = productAttrKeyService.getProductAttrKeyList();
        return success(MallProductAttrKeyConvert.INSTANCE.convertSimpleList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得商品规格键分页")
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:query')")
    public CommonResult<PageResult<MallProductAttrKeyRespVO>> getProductAttrKeyPage(
            @Valid MallProductAttrKeyPageReqVO pageVO) {
        PageResult<MallProductAttrKeyDO> pageResult = productAttrKeyService.getProductAttrKeyPage(pageVO);
        return success(MallProductAttrKeyConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出商品规格键 Excel")
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:export')")
    @OperateLog(type = EXPORT)
    public void exportProductAttrKeyExcel(@Valid MallProductAttrKeyExportReqVO exportReqVO,
            HttpServletResponse response) throws IOException {
        List<MallProductAttrKeyDO> list = productAttrKeyService.getProductAttrKeyList(exportReqVO);
        // 导出 Excel
        List<MallProductAttrKeyExcelVO> datas = MallProductAttrKeyConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "商品规格键.xls", "数据", MallProductAttrKeyExcelVO.class, datas);
    }

}
