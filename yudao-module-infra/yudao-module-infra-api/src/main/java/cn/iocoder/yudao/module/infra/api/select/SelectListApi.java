package cn.iocoder.yudao.module.infra.api.select;

import cn.iocoder.yudao.module.infra.api.select.vo.SelectCommonVo;
import cn.iocoder.yudao.module.infra.api.select.vo.SelectListQueryVo;

import java.util.List;

/**
 * 下拉框的列表查询
 * @author Timeless小帅
 * @date 2022/3/25
 */
public interface SelectListApi {
    /**
     * 获取列表查询
     * @param queryVo 列表查询
     * @return
     */
    public List<SelectCommonVo> selectList(SelectListQueryVo queryVo);
}
