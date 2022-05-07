package cn.iocoder.yudao.module.bpm.framework.flowable.core.behavior;

import cn.iocoder.yudao.module.bpm.service.task.BpmTaskRuleServiceImpl;
import java.util.List;
import lombok.Setter;
import org.flowable.bpmn.model.Activity;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.flowable.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;

public class CustomParallelMultiInstanceBehavior extends ParallelMultiInstanceBehavior {
  @Setter
  private BpmTaskRuleServiceImpl bpmTaskRuleService;

  public CustomParallelMultiInstanceBehavior(Activity activity, AbstractBpmnActivityBehavior originalActivityBehavior) {
    super(activity, originalActivityBehavior);
  }


  /**
   * Handles the parallel case of spawning the instances. Will create child executions accordingly for every instance needed.
   * 处理生成实例的并行情况。将为所需的每个实例相应地创建子执行。当流程实例执行到多实例用户任务时，调用该方法。
   */
  @Override
  protected int createInstances(DelegateExecution multiInstanceRootExecution) {
    // 获取当前执行所在的 BPMN 元素，即多实例用户任务
    FlowElement flowElement = multiInstanceRootExecution.getCurrentFlowElement();
    // 获取流程定义ID
    String processDefinitionId = multiInstanceRootExecution.getProcessDefinitionId();
    // 获取任务定义Key，即流程设计时，多实例用户任务的【ID】
    String taskDefinitionKey = flowElement.getId();
    // 根据分配规则，获取任务候选用户列表
    List<Long> taskCandidateUsers = bpmTaskRuleService.getTaskCandidateUsers(processDefinitionId, taskDefinitionKey);
    // 将候选用户列表设置为变量
    // 获取集合表达式的文本，即流程设计时，多实例用户任务的【集合】
    String expressionText = collectionExpression.getExpressionText();
    multiInstanceRootExecution.setVariable(expressionText, taskCandidateUsers);
    // 调用父类的方法，执行【循环基数次】的for循环,调用execution.setVariableLocal(variableName, value)设置参数
    // 参数名为流程设计时多实例用户任务的【元素变量】，参数值为候选用户列表中的具体元素
    return super.createInstances(multiInstanceRootExecution);
  }

}
