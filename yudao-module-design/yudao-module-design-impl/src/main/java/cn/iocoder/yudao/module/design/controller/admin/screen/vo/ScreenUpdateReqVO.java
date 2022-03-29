package cn.iocoder.yudao.module.design.controller.admin.screen.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("管理后台 - 数据大屏更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ScreenUpdateReqVO extends ScreenBaseVO {

    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空")
    private Long id;

    @ApiModelProperty(value = "描述")
    private String simpleDesc;

    @ApiModelProperty(value = "背景颜色")
    private String bgColor;

    @ApiModelProperty(value = "页面组件")
    private String components;

}
