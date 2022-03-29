package cn.iocoder.yudao.module.design.dal.dataobject.screen;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 数据大屏 DO
 *
 * @author Timeless小帅
 */
@TableName("design_screen")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScreenDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String simpleDesc;
    /**
     * 背景颜色
     */
    private String bgColor;
    /**
     * 屏幕比例X
     */
    private Integer scaleX;
    /**
     * 屏幕比例Y
     */
    private Integer scaleY;
    /**
     * 页面组件
     */
    private String components;
    /**
     * 访问码
     */
    private String viewCode;
    /**
     * 访问量
     */
    private Integer countView;

}
