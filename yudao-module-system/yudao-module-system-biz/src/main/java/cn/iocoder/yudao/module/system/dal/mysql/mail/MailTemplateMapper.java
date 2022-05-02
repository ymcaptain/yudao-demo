package cn.iocoder.yudao.module.system.dal.mysql.mail;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.QueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.mail.vo.template.MailTemplatePageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.mail.MailTemplateDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;


@Mapper
public interface MailTemplateMapper extends BaseMapperX<MailTemplateDO> {

    default PageResult<MailTemplateDO> selectPage(MailTemplatePageReqVO pageReqVO){
        return selectPage(pageReqVO , new QueryWrapperX<MailTemplateDO>()
                .likeIfPresent("name" , pageReqVO.getName())
                .likeIfPresent("username" , pageReqVO.getUsername())
                .likeIfPresent("title" , pageReqVO.getTitle())
                .likeIfPresent("content" , pageReqVO.getContent())
                .eqIfPresent("status" , pageReqVO.getStatus())
                .likeIfPresent("remark" , pageReqVO.getRemark())
        );
    }

    default MailTemplateDO selectOneByCode(String code){
        return selectOne(new QueryWrapperX<MailTemplateDO>()
                .eqIfPresent("code" , code));
    };

    @Select("SELECT id FROM system_mail_template WHERE update_time > #{maxUpdateTime} LIMIT 1")
    Long selectByMaxUpdateTime(Date maxUpdateTime);

    default MailTemplateDO selectOneByAccountId(Long accountId){
        return selectOne(new QueryWrapperX<MailTemplateDO>()
                .eqIfPresent("account_id" , accountId));
    };
}