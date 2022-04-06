package cn.iocoder.yudao.module.design.dal.dataobject.screen;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

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
    // TODO @宋康帅：description，保持和别的模块一致
    private String simpleDesc;
    // TODO @宋康帅：backgroundColor 尽量全称
    /**
     * 背景颜色
     */
    private String bgColor;
    /**
     * 屏幕比例 X
     */
    private Integer scaleX;
    /**
     * 屏幕比例 Y
     */
    private Integer scaleY;
    // TODO @宋康帅：components 是单个组件，还是多个组件？如果是单个，就 component；如果是多个，类型改成 List<String>
    /**
     * 页面组件
     */
    private String components;
    /**
     * 访问码
     */
    private String viewCode;
    // TODO @宋康帅：viewCount ？？？
    /**
     * 访问量
     */
    private Integer countView;

}
