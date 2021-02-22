package cn.iocoder.dashboard.framework.mybatis.dataauth.core.component.getter;

import java.util.function.Supplier;

/**
 * 通过静态方法获取数据权限限制数据
 *
 * @author zzf
 * @date 2021/2/18 11:05
 */
public class StaticDataGetter<R> implements DataGetter<R> {

    private final Supplier<R> supplier;

    public StaticDataGetter(Supplier<R> supplier) {
        this.supplier = supplier;
    }

    @Override
    public R getData() {
        return supplier.get();
    }
}
