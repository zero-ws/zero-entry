package io.mature.boot.argument;

import io.horizon.eon.VString;
import io.vertx.up.eon.KName;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lang : 2023-06-12
 */
public class ArgAtom extends ArgIn {
    private final ConcurrentMap<String, ArgVar> stored = new ConcurrentHashMap<>();

    private ArgAtom(final String[] args) {
        super(args);
        {
            // 0 = path
            this.stored.put(KName.PATH, ArgVar
                .of(KName.PATH));
            // 1 = module
            this.stored.put(KName.MODULE, ArgVar
                .of(KName.MODULE)
                .valueDefault(VString.EMPTY));
        }
    }

    public static ArgAtom of(final String[] args) {
        return new ArgAtom(args);
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
            KName.MOBILE
        );
    }

    @Override
    protected ConcurrentMap<String, ArgVar> definition() {
        return this.stored;
    }
}
