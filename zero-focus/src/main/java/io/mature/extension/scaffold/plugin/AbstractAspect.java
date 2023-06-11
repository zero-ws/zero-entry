package io.mature.extension.scaffold.plugin;

import io.horizon.spi.plugin.AspectPlugin;
import io.vertx.mod.atom.modeling.builtin.DataAtom;
import io.horizon.atom.datamation.KDictAtom;

import java.util.Objects;

/*
 * 抽象层的 Aspect，用于处理配置
 */
public abstract class AbstractAspect implements AspectPlugin {
    protected transient DataAtom atom;
    protected transient PluginQueue queue;

    @Override
    public AspectPlugin bind(final DataAtom atom) {
        this.atom = atom;
        this.queue = new PluginQueue(atom);
        return this;
    }

    @Override
    public AspectPlugin bind(final KDictAtom fabric) {
        if (Objects.nonNull(this.queue)) {
            this.queue.bind(fabric);
        }
        return this;
    }
}
