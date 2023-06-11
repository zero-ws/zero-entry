package io.mature.supply;

import io.vertx.up.plugin.booting.HExtension;
import io.vertx.up.util.Ut;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author lang : 2023-06-10
 */
class LoadIo {


    static Stream<String> ioFiles(final String folder, final boolean oob, final String prefix) {
        final List<String> files = Ut.ioFilesN(folder, null, prefix);
        final Set<HExtension> boots = HExtension.initialize();
        if (!boots.isEmpty() && oob) {
            boots.forEach(boot -> files.addAll(boot.oob(prefix)));
        }
        // 并行
        return files.parallelStream().filter(LoadIo::ensure);
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
