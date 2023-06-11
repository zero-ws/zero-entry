package io.mature.boot.atom;

import io.horizon.atom.program.KVar;

/**
 * 专用参数变量，直接从 {@link KVar} 继承，而 {@link KVar} 中已经包含了当前变量的相关信息：
 * <pre><code>
 *     - name:              变量名称
 *     - type:              变量类型
 *     - alias:             变量别名
 *     - value:             变量值
 *     - valueDefault:      变量默认值（扩展内容）
 * </code></pre>
 *
 * @author lang : 2023-06-11
 */
public class ArgVariable extends KVar {

    private Object valueDefault;

    private ArgVariable(final String name) {
        super(name);
    }

    public static ArgVariable of(final String name) {
        return new ArgVariable(name);
    }

    public ArgVariable valueDefault(final Object valueDefault) {
        this.valueDefault = valueDefault;
        return this;
    }

    @Override
    @SuppressWarnings("all")
    public <T> T value() {
        final Object value = this.value();
        return null == value ? (T) this.valueDefault : (T) value;
    }
}
