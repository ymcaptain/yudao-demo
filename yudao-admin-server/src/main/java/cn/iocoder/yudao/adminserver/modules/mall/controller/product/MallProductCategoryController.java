package cn.iocoder.yudao.adminserver.modules.mall.controller.product;

import cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.category.*;
import cn.iocoder.yudao.adminserver.modules.mall.convert.product.MallProductCategoryConvert;
import cn.iocoder.yudao.adminserver.modules.mall.dal.dataobject.product.MallProductCategoryDO;
import cn.iocoder.yudao.adminserver.modules.mall.service.product.MallProductCategoryService;
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
@Api(tags = "商品分类")
@RestController
@RequestMapping("/mall/product-category")
@Validated
public class MallProductCategoryController {

    @Resource
    private MallProductCategoryService productCategoryService;

    @PostMapping("/create")
    @ApiOperation("创建商品分类")
    @PreAuthorize("@ss.hasPermission('mall:product-category:create')")
    public CommonResult<Long> createProductCategory(@Valid @RequestBody MallProductCategoryCreateReqVO createReqVO) {
        return success(productCategoryService.createProductCategory(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新商品分类")
    @PreAuthorize("@ss.hasPermission('mall:product-category:update')")
    public CommonResult<Boolean> updateProductCategory(@Valid @RequestBody MallProductCategoryUpdateReqVO updateReqVO) {
        productCategoryService.updateProductCategory(updateReqVO);
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改商品分类状态")
    @PreAuthorize("@ss.hasPermission('mall:product-attr-key:update')")
    public CommonResult<Boolean> updateProductAttrStatus(@Valid @RequestBody MallProductCategoryUpdateStatusReqVO reqVO) {
        productCategoryService.updateProductBrandStatus(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除商品分类")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('mall:product-category:delete')")
    public CommonResult<Boolean> deleteProductCategory(@RequestParam("id") Long id) {
        productCategoryService.deleteProductCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得商品分类")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('mall:product-category:query')")
    public CommonResult<MallProductCategoryRespVO> getProductCategory(@RequestParam("id") Long id) {
        MallProductCategoryDO productCategory = productCategoryService.getProductCategory(id);
        return success(MallProductCategoryConvert.INSTANCE.convert(productCategory));
    }

    @GetMapping("/get-roots")
    @ApiOperation("获得商品顶级")
    @PreAuthorize("@ss.hasPermission('mall:product-category:query')")
    public CommonResult<List<MallProductSimpleCategoryRespVO>> getProductCategoryRoots() {
        List<MallProductCategoryDO> categoryRoots = productCategoryService.getProductCategoryRoots();
        return success(MallProductCategoryConvert.INSTANCE.convertRootList(categoryRoots));
    }


    @GetMapping("/list")
    @ApiOperation("获得商品分类列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('mall:product-category:query')")
    public CommonResult<List<MallProductCategoryRespVO>> getProductCategoryList(
            @RequestParam("ids") Collection<Long> ids) {
        List<MallProductCategoryDO> list = productCategoryService.getProductCategoryList(ids);
        return success(MallProductCategoryConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得商品分类分页")
    @PreAuthorize("@ss.hasPermission('mall:product-category:query')")
    public CommonResult<PageResult<MallProductCategoryRespVO>> getProductCategoryPage(
            @Valid MallProductCategoryPageReqVO pageVO) {
        PageResult<MallProductCategoryDO> pageResult = productCategoryService.getProductCategoryPage(pageVO);
        return success(MallProductCategoryConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出商品分类 Excel")
    @PreAuthorize("@ss.hasPermission('mall:product-category:export')")
    @OperateLog(type = EXPORT)
    public void exportProductCategoryExcel(@Valid MallProductCategoryExportReqVO exportReqVO,
            HttpServletResponse response) throws IOException {
        List<MallProductCategoryDO> list = productCategoryService.getProductCategoryList(exportReqVO);
        // 导出 Excel
        List<MallProductCategoryExcelVO> datas = MallProductCategoryConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "商品分类.xls", "数据", MallProductCategoryExcelVO.class, datas);
    }

}
