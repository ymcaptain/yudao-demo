package cn.iocoder.yudao.module.bpm.service.task;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.iocoder.yudao.framework.common.util.json.JsonUtils.toJsonString;
import static cn.iocoder.yudao.module.bpm.enums.ErrorCodeConstants.TASK_CREATE_FAIL_NO_CANDIDATE_USER;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.module.bpm.dal.dataobject.definition.BpmTaskAssignRuleDO;
import cn.iocoder.yudao.module.bpm.dal.dataobject.definition.BpmUserGroupDO;
import cn.iocoder.yudao.module.bpm.enums.definition.BpmTaskAssignRuleTypeEnum;
import cn.iocoder.yudao.module.bpm.service.definition.BpmTaskAssignRuleService;
import cn.iocoder.yudao.module.bpm.service.definition.BpmUserGroupService;
import cn.iocoder.yudao.module.system.api.dept.DeptApi;
import cn.iocoder.yudao.module.system.api.dept.dto.DeptRespDTO;
import cn.iocoder.yudao.module.system.api.permission.PermissionApi;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.google.common.annotations.VisibleForTesting;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.FlowableException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Slf4j
public class BpmTaskRuleServiceImpl {

  @Resource
  private BpmTaskAssignRuleService bpmTaskRuleService;
  @Resource
  private BpmUserGroupService userGroupService;
  @Resource
  private DeptApi deptApi;
  @Resource
  private AdminUserApi adminUserApi;
  @Resource
  private PermissionApi permissionApi;

  @Resource
  private BpmTaskAssignRuleService bpmTaskAssignRuleService;

  public List<Long> getTaskCandidateUsers(String processDefinitionId, String taskDefinitionKey) {
    BpmTaskAssignRuleDO rule = getTaskRule(processDefinitionId, taskDefinitionKey);
    Set<Long> assigneeUserIds = null;
    if (Objects.equals(BpmTaskAssignRuleTypeEnum.ROLE.getType(), rule.getType())) {
      assigneeUserIds = calculateTaskCandidateUsersByRole(rule);
    } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.DEPT_MEMBER.getType(), rule.getType())) {
      assigneeUserIds = calculateTaskCandidateUsersByDeptMember(rule);
    } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.DEPT_LEADER.getType(), rule.getType())) {
      assigneeUserIds = calculateTaskCandidateUsersByDeptLeader(rule);
    } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.POST.getType(), rule.getType())) {
      assigneeUserIds = calculateTaskCandidateUsersByPost(rule);
    } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.USER.getType(), rule.getType())) {
      assigneeUserIds = calculateTaskCandidateUsersByUser(rule);
    } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.USER_GROUP.getType(), rule.getType())) {
      assigneeUserIds = calculateTaskCandidateUsersByUserGroup(rule);
    } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.SCRIPT.getType(), rule.getType())) {
      assigneeUserIds = calculateTaskCandidateUsersByScript(rule);
    }

    // 移除被禁用的用户
    removeDisableUsers(assigneeUserIds);
    // 如果候选人为空，抛出异常 TODO 芋艿：没候选人的策略选择。1 - 挂起；2 - 直接结束；3 - 强制一个兜底人
    if (CollUtil.isEmpty(assigneeUserIds)) {
      log.error("[calculateTaskCandidateUsers][流程任务({}/{}/{}) 任务规则({}) 找不到候选人]", processDefinitionId, taskDefinitionKey, toJsonString(rule));
      throw exception(TASK_CREATE_FAIL_NO_CANDIDATE_USER);
    }
    return new LinkedList<>(assigneeUserIds);
  }

  public BpmTaskAssignRuleDO getTaskRule(String processDefinitionId, String taskDefinitionKey) {

    List<BpmTaskAssignRuleDO> taskRules = bpmTaskAssignRuleService.getTaskAssignRuleListByProcessDefinitionId(processDefinitionId,
        taskDefinitionKey);
    if (CollUtil.isEmpty(taskRules)) {
      throw new FlowableException(StrUtil.format("流程任务({}/{}) 找不到符合的任务规则", processDefinitionId, taskDefinitionKey));
    }
    if (taskRules.size() > 1) {
      throw new FlowableException(StrUtil.format("流程任务({}/{}) 找到过多任务规则({})", processDefinitionId, taskDefinitionKey, taskRules.size()));
    }
    return taskRules.get(0);
  }


  private Set<Long> calculateTaskCandidateUsersByRole(BpmTaskAssignRuleDO rule) {
    return permissionApi.getUserRoleIdListByRoleIds(rule.getOptions());
  }

  private Set<Long> calculateTaskCandidateUsersByDeptMember(BpmTaskAssignRuleDO rule) {
    List<AdminUserRespDTO> users = adminUserApi.getUsersByDeptIds(rule.getOptions());
    return convertSet(users, AdminUserRespDTO::getId);
  }

  private Set<Long> calculateTaskCandidateUsersByDeptLeader(BpmTaskAssignRuleDO rule) {
    List<DeptRespDTO> depts = deptApi.getDepts(rule.getOptions());
    return convertSet(depts, DeptRespDTO::getLeaderUserId);
  }

  private Set<Long> calculateTaskCandidateUsersByPost(BpmTaskAssignRuleDO rule) {
    List<AdminUserRespDTO> users = adminUserApi.getUsersByPostIds(rule.getOptions());
    return convertSet(users, AdminUserRespDTO::getId);
  }

  private Set<Long> calculateTaskCandidateUsersByUser(BpmTaskAssignRuleDO rule) {
    return rule.getOptions();
  }

  private Set<Long> calculateTaskCandidateUsersByUserGroup(BpmTaskAssignRuleDO rule) {
    List<BpmUserGroupDO> userGroups = userGroupService.getUserGroupList(rule.getOptions());
    Set<Long> userIds = new HashSet<>();
    userGroups.forEach(group -> userIds.addAll(group.getMemberUserIds()));
    return userIds;
  }

  private Set<Long> calculateTaskCandidateUsersByScript(BpmTaskAssignRuleDO rule) {
    // 获得对应的脚本
    throw new FlowableException("暂不支持脚本");
//    List<BpmTaskAssignScript> scripts = new ArrayList<>(rule.getOptions().size());
//    rule.getOptions().forEach(id -> {
//      BpmTaskAssignScript script = scriptMap.get(id);
//      if (script == null) {
//        throw exception(TASK_ASSIGN_SCRIPT_NOT_EXISTS, id);
//      }
//      scripts.add(script);
//    });
//    // 逐个计算任务
//    Set<Long> userIds = new HashSet<>();
//    scripts.forEach(script -> CollUtil.addAll(userIds, script.calculateTaskCandidateUsers(task)));
//    return userIds;
  }

  @VisibleForTesting
  void removeDisableUsers(Set<Long> assigneeUserIds) {
    if (CollUtil.isEmpty(assigneeUserIds)) {
      return;
    }
    //TODO 芋艿 这里有数据权限的问题。默认会加上数据权限 dept_id IN (deptId). 导致查询不到数据
    Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(assigneeUserIds);
    assigneeUserIds.removeIf(id -> {
      AdminUserRespDTO user = userMap.get(id);
      return user == null || !CommonStatusEnum.ENABLE.getStatus().equals(user.getStatus());
    });
  }

}
