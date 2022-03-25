package cn.iocoder.yudao.module.infra.api.select;

import cn.iocoder.yudao.module.infra.api.select.vo.SelectCommonVo;
import cn.iocoder.yudao.module.infra.api.select.vo.SelectListQueryVo;
import cn.iocoder.yudao.module.infra.dal.mysql.select.SelectListMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * SelectListApiImpl:
 *
 * @author Timeless小帅
 * @date 2022/3/25
 */
@Service
@Validated
public class SelectListApiImpl implements SelectListApi{

    @Resource
    SelectListMapper selectListMapper;

    /**
     * 获取列表查询
     *
     * @param queryVo 列表查询
     * @return
     */
    @Override
    public List<SelectCommonVo> selectList(SelectListQueryVo queryVo) {
        return selectListMapper.selectList(queryVo);
    }
}
