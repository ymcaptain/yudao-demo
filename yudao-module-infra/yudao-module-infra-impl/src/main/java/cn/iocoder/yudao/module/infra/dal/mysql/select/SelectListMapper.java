package cn.iocoder.yudao.module.infra.dal.mysql.select;

import cn.iocoder.yudao.module.infra.api.select.vo.SelectCommonVo;
import cn.iocoder.yudao.module.infra.api.select.vo.SelectListQueryVo;

import java.util.List;

/**
 * selectListMapper:
 *
 * @author Timeless小帅
 * @date 2022/3/25
 */
public interface SelectListMapper {

    public List<SelectCommonVo> selectList(SelectListQueryVo queryVo);
}
