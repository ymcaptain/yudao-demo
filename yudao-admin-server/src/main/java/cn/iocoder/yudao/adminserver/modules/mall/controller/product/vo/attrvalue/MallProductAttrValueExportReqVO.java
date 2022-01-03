package cn.iocoder.yudao.adminserver.modules.mall.controller.product.vo.attrvalue;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * @author aquan
 */
@ApiModel(value = "商品规格值 Excel 导出 Request VO", description = "参数和 MallProductAttrValuePageReqVO 是一致的")
@Data
public class MallProductAttrValueExportReqVO {

    @ApiModelProperty(value = "规格键编号")
    private Long attrKeyId;

    @ApiModelProperty(value = "规格值名字")
    private String name;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
