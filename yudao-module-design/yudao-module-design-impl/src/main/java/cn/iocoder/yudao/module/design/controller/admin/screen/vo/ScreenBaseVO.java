package cn.iocoder.yudao.module.design.controller.admin.screen.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
* 数据大屏 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class ScreenBaseVO {

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

}
