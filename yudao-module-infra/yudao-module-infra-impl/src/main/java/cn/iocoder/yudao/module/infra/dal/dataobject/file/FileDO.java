package cn.iocoder.yudao.module.infra.dal.dataobject.file;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.InputStream;

/**
 * 文件表
 * 每次文件上传，都会记录一条记录到该表中
 *
 * @author 芋道源码
 */
@Data
@TableName("infra_file")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDO extends BaseDO {

    /**
     * 编号，数据库自增
     */
    private Long id;
    /**
     * 配置编号
     *
     * 关联 {@link FileConfigDO#getId()}
     */
    private Long configId;
    /**
     * 路径，即文件名
     */
    private String path;
    /**
     * 名称
     */
    private String name;
    /**
     * 图片分组
     */
    // TODO @宋康帅：文件分组。字段要么用 group；要么担心关键字，就用 category；不然一个文件 DO 类，里面有 imgGroup 这样的命名，不太合理
    private Long imgGroup;
    /**
     * 访问地址
     */
    private String url;
    /**
     * 文件类型
     *
     * 通过 {@link cn.hutool.core.io.FileTypeUtil#getType(InputStream)} 获取
     */
    @TableField(value = "`type`")
    private String type;
    /**
     * 文件大小
     */
    private Integer size;

}
