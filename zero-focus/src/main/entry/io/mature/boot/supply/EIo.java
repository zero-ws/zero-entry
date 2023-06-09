package io.mature.boot.supply;

import io.horizon.eon.VPath;
import io.horizon.eon.VString;
import io.horizon.eon.spec.VWeb;
import io.modello.atom.app.KIntegration;

import java.util.Objects;

/**
 * @author lang : 2023-06-10
 */
class EIo {

    static String ioPartyB(final String key, final KIntegration integration) {
        Objects.requireNonNull(key);
        final String hit = key.replace("/", "");
        /*
         * runtime/{vendor}/{hit}/{vendor}.json
         */
        return VWeb.RUNTIME + VString.SLASH
            + integration.getVendorConfig() + VString.SLASH
            + hit + VString.SLASH
            + integration.getVendor() + VString.DOT + VPath.SUFFIX.JSON;
    }
}
