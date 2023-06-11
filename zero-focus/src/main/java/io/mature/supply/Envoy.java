package io.mature.supply;

import java.util.stream.Stream;

/**
 * 和内置的 {@link io.vertx.up.supply.Electy} 形成对比
 * <pre><code>
 *     1. {@link io.vertx.up.supply.Electy} 主要负责核心框架运行时处理
 *     2. {@link Envoy} 则负责扩展框架中启动时的数据提供，针对不同的入口启动器
 * </code></pre>
 *
 * @author lang : 2023-06-11
 */
public class Envoy {
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
    public static Stream<String> ioFile(final String folder, final boolean oob, final String prefix) {
        return LoadIo.ioFiles(folder, oob, prefix);
    }
}
