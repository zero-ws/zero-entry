package io.vertx.boot.load;

import io.vertx.up.plugin.booting.HExtension;
import io.vertx.up.util.Ut;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author lang : 2023-06-10
 */
public class LoadIo {

    /**
     * 读取文件夹下的所有文件
     * <pre><code>
     *     1. args[0]: folder / 读取数据文件的目录路径信息
     *     2. args[1]: oob / 是否加载OOB数据，开启OOB Loader
     *     3. args[2]: prefix / 满足条件的文件匹配前缀
     * </code></pre>
     *
     * @param folder 文件夹
     * @param oob    是否包含子文件夹
     * @param prefix 文件前缀
     *
     * @return {@link java.util.stream.Stream}
     */
    public static Stream<String> ioFiles(final String folder, final boolean oob, final String prefix) {
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
