package cn.iocoder.yudao.module.bpm.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("会签类型")
public class TaskTypeVo {
    /**
     * 会签类型（串行，并行）
     */
    private Object type;

    /**
     * 会签人员KEY
     */
    private String assignee;

    /**
     * 会签人员集合KEY
     */
    private String assigneeList;
}
