package cn.iocoder.yudao.adminserver.modules.mall.controller.product;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.brand.*;
import cn.iocoder.yudao.adminserver.modules.mall.convert.product.MallProductBrandConvert;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductBrandDO;
import cn.iocoder.yudao.adminserver.modules.mall.service.product.MallProductBrandService;
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
@Api(tags = "商品品牌")
@RestController
@RequestMapping("/mall/product-brand")
@Validated
public class MallProductBrandController {

    @Resource
    private MallProductBrandService productBrandService;

    @PostMapping("/create")
    @ApiOperation("创建商品品牌")
    @PreAuthorize("@ss.hasPermission('mall:product-brand:create')")
    public CommonResult<Long> createProductBrand(@Valid @RequestBody MallProductBrandCreateReqVO createReqVO) {
        return success(productBrandService.createProductBrand(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新商品品牌")
    @PreAuthorize("@ss.hasPermission('mall:product-brand:update')")
    public CommonResult<Boolean> updateProductBrand(@Valid @RequestBody MallProductBrandUpdateReqVO updateReqVO) {
        productBrandService.updateProductBrand(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改商品品牌状态")
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:update')")
    public CommonResult<Boolean> updateProductAttrStatus(@Valid @RequestBody MallProductBrandUpdateStatusReqVO reqVO) {
        productBrandService.updateProductBrandStatus(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除商品品牌")
    @ApiImplicitParam(name = "id", value = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('mall:product-brand:delete')")
    public CommonResult<Boolean> deleteProductBrand(@RequestParam("id") Long id) {
        productBrandService.deleteProductBrand(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得商品品牌")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Integer.class)
    @PreAuthorize("@ss.hasPermission('mall:product-brand:query')")
    public CommonResult<MallProductBrandRespVO> getProductBrand(@RequestParam("id") Long id) {
        MallProductBrandDO productBrand = productBrandService.getProductBrand(id);
        return success(MallProductBrandConvert.INSTANCE.convert(productBrand));
    }

    @GetMapping("/list")
    @ApiOperation("获得商品品牌列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('mall:product-brand:query')")
    public CommonResult<List<MallProductBrandRespVO>> getProductBrandList(
            @RequestParam("ids") Collection<Long> ids) {
        List<MallProductBrandDO> list = productBrandService.getProductBrandList(ids);
        return success(MallProductBrandConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得商品品牌分页")
    @PreAuthorize("@ss.hasPermission('mall:product-brand:query')")
    public CommonResult<PageResult<MallProductBrandRespVO>> getProductBrandPage(
            @Valid MallProductBrandPageReqVO pageVO) {
        PageResult<MallProductBrandDO> pageResult = productBrandService.getProductBrandPage(pageVO);
        return success(MallProductBrandConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出商品品牌 Excel")
    @PreAuthorize("@ss.hasPermission('mall:product-brand:export')")
    @OperateLog(type = EXPORT)
    public void exportProductBrandExcel(@Valid MallProductBrandExportReqVO exportReqVO,
            HttpServletResponse response) throws IOException {
        List<MallProductBrandDO> list = productBrandService.getProductBrandList(exportReqVO);
        // 导出 Excel
        List<MallProductBrandExcelVO> datas = MallProductBrandConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "商品品牌.xls", "数据", MallProductBrandExcelVO.class, datas);
    }

}
