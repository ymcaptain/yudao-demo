package cn.iocoder.yudao.adminserver.modules.bpm.dal.dataobject.definition;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;

/**
 * Bpm 流程定义的拓展表
 * 主要解决 Activiti {@link ProcessDefinition} 不支持拓展字段，所以新建拓展表
 *
 * @author 芋道源码
 */
@TableName(value = "bpm_process_definition_ext", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmProcessDefinitionExtDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 流程定义的编号
     *
     * 关联 {@link ProcessDefinition#getId()}
     */
    private String processDefinitionId;
    /**
     * 流程模型的编号
     *
     * 关联 {@link Model#getId()}
     */
    private String modelId;
    /**
     * 描述
     */
    private String description;
    /**
     * 表单编号
     *
     * 关联 {@link BpmFormDO#getId()}
     */
    private Long formId;

}
