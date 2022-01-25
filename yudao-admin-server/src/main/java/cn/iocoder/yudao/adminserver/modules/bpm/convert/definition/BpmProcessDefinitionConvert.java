package cn.iocoder.yudao.adminserver.modules.bpm.convert.definition;

import cn.iocoder.yudao.adminserver.modules.bpm.controller.definition.vo.process.BpmProcessDefinitionPageItemRespVO;
import cn.iocoder.yudao.adminserver.modules.bpm.controller.definition.vo.process.BpmProcessDefinitionRespVO;
import cn.iocoder.yudao.adminserver.modules.bpm.dal.dataobject.definition.BpmProcessDefinitionExtDO;
import cn.iocoder.yudao.adminserver.modules.bpm.dal.dataobject.definition.BpmFormDO;
import cn.iocoder.yudao.adminserver.modules.bpm.service.definition.dto.BpmDefinitionCreateReqDTO;
import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import org.activiti.engine.impl.persistence.entity.SuspensionState;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

/**
 * Bpm 流程定义的 Convert
 *
 * @author yunlong.li
 */
@Mapper
public interface BpmProcessDefinitionConvert {

    BpmProcessDefinitionConvert INSTANCE = Mappers.getMapper(BpmProcessDefinitionConvert.class);

    default List<BpmProcessDefinitionPageItemRespVO> convertList(List<ProcessDefinition> list, Map<String, Deployment> deploymentMap,
                                                                 Map<String, BpmProcessDefinitionExtDO> processDefinitionDOMap, Map<Long, BpmFormDO> formMap) {
        return CollectionUtils.convertList(list, definition -> {
            Deployment deployment = definition.getDeploymentId() != null ? deploymentMap.get(definition.getDeploymentId()) : null;
            BpmProcessDefinitionExtDO definitionDO = processDefinitionDOMap.get(definition.getId());
            BpmFormDO form = definitionDO != null ? formMap.get(definitionDO.getFormId()) : null;
            return convert(definition, deployment, definitionDO, form);
        });
    }

    default BpmProcessDefinitionPageItemRespVO convert(ProcessDefinition bean, Deployment deployment,
                                                       BpmProcessDefinitionExtDO processDefinitionDO, BpmFormDO form) {
        BpmProcessDefinitionPageItemRespVO respVO = convert(bean);
        respVO.setSuspensionState(bean.isSuspended() ? SuspensionState.SUSPENDED.getStateCode() : SuspensionState.ACTIVE.getStateCode());
        if (deployment != null) {
            respVO.setDeploymentTime(deployment.getDeploymentTime());
        }
        if (form != null) {
            respVO.setFormId(form.getId());
            respVO.setFormName(form.getName());
        }
        if (processDefinitionDO != null) {
            respVO.setDescription(processDefinitionDO.getDescription());
        }
        return respVO;
    }

    BpmProcessDefinitionPageItemRespVO convert(ProcessDefinition bean);

    BpmProcessDefinitionExtDO convert2(BpmDefinitionCreateReqDTO bean);

    default List<BpmProcessDefinitionRespVO> convertList3(List<ProcessDefinition> list,
                                                          Map<String, BpmProcessDefinitionExtDO> processDefinitionDOMap) {
        return CollectionUtils.convertList(list, processDefinition -> {
            BpmProcessDefinitionRespVO respVO = convert3(processDefinition);
            BpmProcessDefinitionExtDO processDefinitionExtDO = processDefinitionDOMap.get(processDefinition.getId());
            if (processDefinitionExtDO != null) {
                respVO.setFormId(processDefinitionExtDO.getFormId());
            }
            return respVO;
        });
    }

    @Mapping(source = "suspended", target = "suspensionState", qualifiedByName = "convertSuspendedToSuspensionState")
    BpmProcessDefinitionRespVO convert3(ProcessDefinition bean);

    @Named("convertSuspendedToSuspensionState")
    default Integer convertSuspendedToSuspensionState(boolean suspended) {
        return suspended ? SuspensionState.SUSPENDED.getStateCode() :
                SuspensionState.ACTIVE.getStateCode();
    }

}
