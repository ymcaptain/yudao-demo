package cn.iocoder.yudao.module.design.controller.admin.screen.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 数据大屏分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ScreenPageReqVO extends PageParam {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "屏幕比例X")
    private Integer scaleX;

    @ApiModelProperty(value = "屏幕比例Y")
    private Integer scaleY;

    @ApiModelProperty(value = "设计预览图")
    private String designImgId;

    @ApiModelProperty(value = "禁用状态")
    private Integer state;

    @ApiModelProperty(value = "访问码")
    private String viewCode;

    @ApiModelProperty(value = "访问量")
    private Integer countView;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
