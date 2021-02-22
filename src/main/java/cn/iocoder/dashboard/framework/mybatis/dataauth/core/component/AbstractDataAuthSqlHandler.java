package cn.iocoder.dashboard.framework.mybatis.dataauth.core.component;

import cn.iocoder.dashboard.framework.mybatis.dataauth.core.component.getter.DataGetter;
import cn.iocoder.dashboard.framework.mybatis.dataauth.core.entity.DataAuthCache;
import cn.iocoder.dashboard.framework.mybatis.dataauth.core.entity.DataAuthConstants;
import cn.iocoder.dashboard.framework.mybatis.dataauth.core.entity.SqlHandlerResult;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.reflection.property.PropertyNamer;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 用于数据权限限制的处理器抽象类
 * <p>
 * 后续要需要考虑：
 * 1、需要多张表实现限制的，如
 * 需要JOIN sys_dept t1 xxx JOIN sys_user t2 ON t1.xx = t2.xx
 * 然后在where中通过t2.xxx = xxx进行数据限制
 * 2、无需join表的情况，即需要限制的表中直接包含指定字段，只需要加入WHERE条件即可
 *
 * @author zzf
 * @date 11:05 2021/2/4
 */
@Slf4j
public abstract class AbstractDataAuthSqlHandler<T> {

    /**
     * 该表对应的实体类
     * 如：
     * sys_role
     * sys_user
     */
    protected final Class<?> entityClass;

    /**
     * 该表对应的实体类
     */
    protected final String tableName;

    /**
     * 表别名
     */
    protected final String tableAlias;

    /**
     * 关联字段名
     */
    protected final String relationColumnName;

    /**
     * where字段名
     */
    protected final String whereColumnName;

    /**
     * 获取数据的对象
     */
    protected final DataGetter<T> dataGetter;

    /**
     * sql中的表达式
     */
    protected final Expression operator;


    protected <R> AbstractDataAuthSqlHandler(Class<?> entityClass,
                                             String tableAlias,
                                             DataGetter<T> dataGetter,
                                             SFunction<R, ?> relationFieldFunction,
                                             SFunction<R, ?> whereFieldFunction,
                                             Expression operator) {
        this.entityClass = entityClass;
        this.tableAlias = tableAlias;
        this.dataGetter = dataGetter;
        this.operator = operator;

        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
        Assert.notNull(tableInfo, DataAuthConstants.notExistsTableInfo(entityClass.getSimpleName()));
        this.tableName = tableInfo.getTableName();

        // 获取relationColumnName
        String relationFieldName = PropertyNamer.methodToProperty(LambdaUtils.resolve(relationFieldFunction).getImplMethodName());
        if (relationFieldName.equals(tableInfo.getKeyProperty())) {
            this.relationColumnName = tableInfo.getKeyColumn();
        } else {
            Optional<String> columnNameOptional = tableInfo.getFieldList().stream()
                    .filter(s -> s.getProperty().equals(relationFieldName))
                    .map(TableFieldInfo::getColumn)
                    .findFirst();

            if (columnNameOptional.isPresent()) {
                this.relationColumnName = columnNameOptional.get();
            } else {
                throw new IllegalArgumentException(DataAuthConstants.notExistsFieldInfo(relationFieldName));
            }
        }


        // 获取whereFieldName
        String whereFieldName = PropertyNamer.methodToProperty(LambdaUtils.resolve(whereFieldFunction).getImplMethodName());
        if (whereFieldName.equals(tableInfo.getKeyProperty())) {
            this.whereColumnName = tableInfo.getKeyColumn();
        } else {
            Optional<String> whereFiledOptional = tableInfo.getFieldList().stream()
                    .filter(s -> s.getProperty().equals(whereFieldName))
                    .map(TableFieldInfo::getColumn)
                    .findFirst();

            if (whereFiledOptional.isPresent()) {
                this.whereColumnName = whereFiledOptional.get();
            } else {
                throw new IllegalArgumentException(DataAuthConstants.notExistsFieldInfo(whereFieldName));
            }
        }
    }


    /**
     * 进行数据权限限制的SQL处理
     */
    public SqlHandlerResult doParse(Select selectStatement, Class<?> entityClass) {
        T data = dataGetter.getData();
        PlainSelect selectBody = (PlainSelect) selectStatement.getSelectBody();
        //判断数据是否为空，且为空时是否处理
        if (data == null) {
            if (parseIfDataAbsent()) {
                //空数据权限处理
                EqualsTo equalsTo = new EqualsTo();
                equalsTo.setRightExpression(new LongValue(1));
                equalsTo.setLeftExpression(new LongValue(0));
                //在where子句中添加 1 = 0 条件, 并 and 原有条件
                AndExpression andExpression = new AndExpression(equalsTo, selectBody.getWhere());
                selectBody.setWhere(andExpression);
            }
            return SqlHandlerResult.nonData(parseIfDataAbsent());
        } else {
            if (dataIsFull(data)) {
                // 拥有全部权限，此处不对SQL做任何处理
                return SqlHandlerResult.hadAll();
            } else {
                // 正常处理
                Table fromItem = (Table) selectBody.getFromItem();
                aliasHandle(selectBody, fromItem);
                joinHandle(entityClass, selectBody, fromItem);
                whereHandle(selectBody);
            }
        }
        return SqlHandlerResult.handle();
    }

    /**
     * 别名处理
     * 如果from的表没有别名，手动给他加个别名，并给所有 查询列和 where条件中的列增加别名前缀
     */
    public void aliasHandle(PlainSelect selectBody, Table fromItem) {
        if (fromItem.getAlias() == null || fromItem.getAlias().getName() == null) {
            fromItem.setAlias(new Alias(DataAuthConstants.DEFAULT_ALIAS));
            selectBody.getSelectItems().forEach(s ->
                    s.accept(new ExpressionVisitorAdapter() {
                        @Override
                        public void visit(Column column) {
                            column.setTable(fromItem);
                        }
                    })
            );

            Expression where = selectBody.getWhere();
            if (where != null) {
                where.accept(new ExpressionVisitorAdapter() {
                    @Override
                    public void visit(Column column) {
                        column.setTable(fromItem);
                    }
                });
            }
        }
    }

    /**
     * JOIN子句处理
     * <p>
     * 将数据限制表通过JOIN的方式加入
     */
    public void joinHandle(Class<?> entityClass, PlainSelect selectBody, Table fromItem) {
        // 假如要实现 LEFT JOIN t2 ON t2.id2 = t1.id1
        Table limitTable = getLimitTable();
        Join join = new Join();// JOIN
        join.setLeft(true);// LEFT JOIN
        join.setRightItem(limitTable);// LEFT JOIN t2

        EqualsTo equalsTo = new EqualsTo();// =

        Column limitRelationColumn = new Column(relationColumnName);// id2
        limitRelationColumn.setTable(limitTable);// t2.id2

        Column targetRelationColumn = new Column(DataAuthCache.getFieldName(getId(), entityClass));// id1
        targetRelationColumn.setTable(fromItem);// t1.id1

        equalsTo.setLeftExpression(limitRelationColumn);
        equalsTo.setRightExpression(targetRelationColumn);// t2.id2 = t1.id1
        join.setOnExpression(equalsTo);// LEFT JOIN t2 ON t2.id2 = t1.id1

        List<Join> joins = selectBody.getJoins();
        if (joins == null) {
            selectBody.setJoins(Collections.singletonList(join));
        } else {
            joins.add(join);
        }
    }

    /**
     * WHERE子句处理
     * <p>
     * 添加数据权限条件
     */
    public void whereHandle(PlainSelect selectBody) {
        Column whereColumn = new Column(whereColumnName);
        whereColumn.setTable(getLimitTable());

        setOperatorValue();

        AndExpression andExpression = new AndExpression(operator, selectBody.getWhere());
        selectBody.setWhere(andExpression);
    }

    protected Table getLimitTable() {
        Table limitTable = new Table(this.tableName);
        limitTable.setAlias(new Alias(this.tableAlias));
        return limitTable;
    }

    /**
     * SqlHandler唯一标识，用于{@link DataAuthStrategy 中使用}
     */
    abstract public int getId();

    /**
     * 如果数据为空，是否处理SQL
     * <p>
     * 如果为true，则表示返回空数据，系统将增加 1 = 0条件
     * 如果为false，则不改变sql
     */
    protected abstract boolean parseIfDataAbsent();

    /**
     * 插入数据限制条件的值
     * {@link #operator} {@code operator.setRightExpression(getData())}
     * 这个方法可以写成默认的，对所有类型进行遍历并实现所有类型的插入方法。
     * 但是好麻烦。。。要考虑 =、 IN、 LIKE(三种) 等表达式，还有不同的数据类型balabala一大堆
     */
    protected abstract void setOperatorValue();

    /**
     * 判断该数据是否属于全部权限数据
     *
     * @param data 数据权限数据
     */
    protected abstract boolean dataIsFull(T data);


}