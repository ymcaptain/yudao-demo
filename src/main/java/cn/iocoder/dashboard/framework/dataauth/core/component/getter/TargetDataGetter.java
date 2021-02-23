package cn.iocoder.dashboard.framework.dataauth.core.component.getter;

import java.util.function.Function;

/**
 * 通过执行对象方法获取数据权限限制数据
 *
 * @author zzf
 * @date 2021/2/18 11:05
 */
public class TargetDataGetter<T, R> implements DataGetter<R> {

    private final Function<T, R> function;

    private final T target;

    public TargetDataGetter(T target, Function<T, R> function) {
        this.function = function;
        this.target = target;
    }

    @Override
    public R getData() {
        return function.apply(target);
    }
}
