package io.mature.boot.supply;

import io.horizon.eon.VPath;
import io.horizon.eon.VSpec;
import io.horizon.eon.VString;
import io.modello.atom.app.KIntegration;
import io.vertx.up.plugin.booting.HExtension;
import io.vertx.up.util.Ut;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author lang : 2023-06-10
 */
class EIo {

    static Stream<String> ioFiles(final String folder, final boolean oob, final String prefix) {
        final List<String> files = Ut.ioFilesN(folder, null, prefix);
        final Set<HExtension> boots = HExtension.initialize();
        if (!boots.isEmpty() && oob) {
            boots.forEach(boot -> files.addAll(boot.oob(prefix)));
        }
        // 并行
        return files.parallelStream().filter(EIo::ensure);
    }

    static String ioPartyB(final String key, final KIntegration integration) {
        Objects.requireNonNull(key);
        final String hit = key.replace("/", "");
        /*
         * runtime/{vendor}/{hit}/{vendor}.json
         */
        return VSpec.Web.RUNTIME + VString.SLASH
            + integration.getVendorConfig() + VString.SLASH
            + hit + VString.SLASH
            + integration.getVendor() + VString.DOT + VPath.SUFFIX.JSON;
    }

    private static boolean ensure(final String filename) {
        // File not null
        if (Ut.isNil(filename)) {
            return false;
        }
        // Ignore "~" start
        if (filename.contains("~")) {
            return false;
        }
        // Excel only
        return filename.endsWith("xlsx") || filename.endsWith("xls");
    }
}
