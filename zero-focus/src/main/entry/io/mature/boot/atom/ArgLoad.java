package io.mature.boot.atom;

import io.horizon.eon.VString;
import io.vertx.up.eon.KName;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lang : 2023-06-11
 */
public class ArgLoad extends ArgIn {
    private final ConcurrentMap<String, ArgVariable> stored = new ConcurrentHashMap<>();

    private ArgLoad(final String[] args) {
        super(args);
        {
            // 0 = path
            this.stored.put(KName.PATH, ArgVariable
                .of(KName.PATH)
                .valueDefault("init/oob"));
            // 1 = oob
            this.stored.put("oob", ArgVariable
                .of("oob")
                .valueDefault(Boolean.TRUE)
                .bind(Boolean.class));
            // 2 = prefix
            this.stored.put(KName.PREFIX, ArgVariable
                .of(KName.PREFIX)
                .valueDefault(VString.EMPTY));
        }
    }

    public static ArgLoad of(final String[] args) {
        return new ArgLoad(args);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T value(final String name) {
        return (T) this.stored.getOrDefault(name, null);
    }

    @Override
    protected List<String> names() {
        return List.of(
            KName.PATH,
            "oob",          // 默认 TRUE
            KName.PREFIX    // 默认 ""
        );
    }

    @Override
    protected ConcurrentMap<String, ArgVariable> definition() {
        return this.stored;
    }
}
