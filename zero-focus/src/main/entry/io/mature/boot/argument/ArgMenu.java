package io.mature.boot.argument;

import io.vertx.up.eon.KName;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lang : 2023-06-12
 */
public class ArgMenu extends ArgIn {
    private final ConcurrentMap<String, ArgVar> stored = new ConcurrentHashMap<>();

    private ArgMenu(final String[] args) {
        super(args);
        {
            // 0 = path
            this.stored.put(KName.PATH, ArgVar
                .of(KName.PATH)
                .valueDefault("init/map/menu.yml"));
        }
    }

    public static ArgMenu of(final String[] args) {
        return new ArgMenu(args);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T value(final String name) {
        return (T) this.stored.getOrDefault(name, null);
    }

    @Override
    protected List<String> names() {
        return List.of(
            KName.PATH
        );
    }

    @Override
    protected ConcurrentMap<String, ArgVar> definition() {
        return this.stored;
    }
}
