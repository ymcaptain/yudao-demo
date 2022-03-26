package cn.iocoder.yudao.module.design.controller.admin.screen.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import com.alibaba.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;


/**
 * 数据大屏 Excel VO
 *
 * @author Timeless小帅
 */
@Data
public class ScreenExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("标题")
    private String title;

    @ExcelProperty("屏幕比例X")
    private Integer scaleX;

    @ExcelProperty("屏幕比例Y")
    private Integer scaleY;

    @ExcelProperty("设计预览图")
    private String designImgId;

    @ExcelProperty(value = "禁用状态", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer state;

    @ExcelProperty("访问码")
    private String viewCode;

    @ExcelProperty("访问量")
    private Integer countView;

    @ExcelProperty("创建时间")
    private Date createTime;

}
