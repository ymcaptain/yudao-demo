package cn.iocoder.yudao.framework.activiti.core.util;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.util.io.BytesStreamSource;

import javax.xml.bind.Element;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Activiti 工具类
 *
 * @author 芋道源码
 */
public class ActivitiUtils {

    static {
        setAuthenticationThreadLocal();
    }

    // ========== Authentication 相关 ==========

    /**
     * 反射修改 Authentication 的 authenticatedUserIdThreadLocal 静态变量，使用 TTL 线程变量
     * 目的：保证 @Async 等异步执行时，变量丢失的问题
     */
    private static void setAuthenticationThreadLocal() {
        ReflectUtil.setFieldValue(Authentication.class, "authenticatedUserIdThreadLocal",
                new TransmittableThreadLocal<String>());
    }

    public static void setAuthenticatedUserId(Long userId) {
        Authentication.setAuthenticatedUserId(String.valueOf(userId));
    }

    public static void clearAuthenticatedUserId() {
        Authentication.setAuthenticatedUserId(null);
    }

    // ========== BPMN XML 相关 ==========


    /**
     * 构建对应的 BPMN Model
     *
     * @param bpmnBytes 原始的 BPMN XML 字节数组
     * @return BPMN Model
     */
    public static BpmnModel buildBpmnModel(byte[] bpmnBytes) {
        // 转换成 BpmnModel 对象
        BpmnXMLConverter converter = new BpmnXMLConverter();
        return converter.convertToBpmnModel(new BytesStreamSource(bpmnBytes), true, true);
    }

    public static <T extends FlowElement> List<T> getBpmnModelElements(BpmnModel model, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        model.getProcesses().forEach(process -> {
            process.getFlowElements().forEach(flowElement -> {
                if (flowElement.getClass().isAssignableFrom(clazz)) {
                    result.add((T) flowElement);
                }
            });
        });
        return result;
    }

}
