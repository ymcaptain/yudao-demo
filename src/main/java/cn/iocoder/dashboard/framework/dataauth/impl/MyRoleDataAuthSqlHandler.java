package cn.iocoder.dashboard.framework.dataauth.impl;

import cn.iocoder.dashboard.framework.dataauth.core.component.AbstractDataAuthSqlHandler;
import cn.iocoder.dashboard.framework.dataauth.core.component.getter.TargetDataGetter;
import cn.iocoder.dashboard.modules.system.dal.dataobject.user.SysUserDO;
import cn.iocoder.dashboard.modules.system.service.permission.SysPermissionService;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.ValueListExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 角色数据权限限制sql处理器
 *
 * @author zzf
 * @date 2021/2/19 15:58
 */
public class MyRoleDataAuthSqlHandler extends AbstractDataAuthSqlHandler<Set<Long>> {

    public MyRoleDataAuthSqlHandler(SysPermissionService permissionService) {
        super(SysUserDO.class,
                "dr_usr",
                new TargetDataGetter<>(permissionService, SysPermissionService::getCurrentUserDataPermission),
                SysUserDO::getId,
                SysUserDO::getDeptId,
                new InExpression());
    }


    @Override
    public int getId() {
        return SqlHandlerIdEnum.ROLE.getId();
    }

    @Override
    protected boolean parseIfDataAbsent() {
        return false;
    }

    @Override
    protected void setOperatorValue() {
        InExpression operator = (InExpression) this.operator;

        Column whereColumn = new Column(whereColumnName);
        whereColumn.setTable(getLimitTable());
        operator.setLeftExpression(whereColumn);

        ValueListExpression listExpression = new ValueListExpression();
        Set<Long> data = dataGetter.getData();
        if (data != null) {
            List<Expression> expressions = new ArrayList<>();
            data.forEach(s -> {
                LongValue stringValue = new LongValue(s);
                expressions.add(stringValue);
            });
            listExpression.setExpressionList(new ExpressionList(expressions));
            operator.setRightExpression(listExpression);
        }
    }

    @Override
    protected boolean dataIsFull(Set<Long> data) {
        return false;
    }

}
