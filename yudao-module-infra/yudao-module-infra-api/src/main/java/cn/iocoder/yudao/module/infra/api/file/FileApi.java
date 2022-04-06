package cn.iocoder.yudao.module.infra.api.file;

import cn.hutool.core.util.IdUtil;

/**
 * 文件 API 接口
 *
 * @author 芋道源码
 */
public interface FileApi {

    // TODO @宋康帅：可以 default 一个，不需要传递 group，直接默认其它
    // TODO @宋康帅：group 的注释
    /**
     * 保存文件，并返回文件的访问路径
     *
     * @param content 文件内容
     * @return 文件路径
     */
   default String createFile(Long group, byte[] content) throws Exception {
       return createFile(IdUtil.fastUUID(), group, content);
   }

    /**
     * 保存文件，并返回文件的访问路径
     *
     * @param path 文件路径
     * @param content 文件内容
     * @return 文件路径
     */
    String createFile(String path, Long group, byte[] content) throws Exception;

}
