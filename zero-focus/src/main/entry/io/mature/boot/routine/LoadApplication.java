package io.mature.boot.routine;

import io.horizon.uca.boot.KLauncher;
import io.mature.boot.atom.ArgLoad;
import io.vertx.boot.supply.Electy;
import io.vertx.core.Vertx;
import io.vertx.up.eon.KName;
import io.vertx.up.util.Ut;

import static io.vertx.mod.ke.refine.Ke.LOG;

/**
 * 数据导入时参数必须要带，以确定当前参数是否包含 OOB 导入流程
 * <pre><code>
 * java -jar xxx.jar init/oop true
 * </code></pre>
 *
 * @author lang : 2023-06-10
 */
public class LoadApplication {

    public static void run(final Class<?> clazz, final String... args) {
        /*
         * 不做任何输入限制，都带有默认值处理
         * 0 - 导入数据的基本路径
         * 1 - 是否包含 OOB 数据
         * 2 - 是否带有 prefix 参数做文件过滤
         */
        final ArgLoad input = ArgLoad.of(args);
        final String path = input.value(KName.PATH);
        final Boolean oob = input.value("oob");
        final String prefix = input.value(KName.PREFIX);


        /*
         *  路径构造，此处路径构造必须是 Production
         *  由于 inst 工具构造的所有信息都是在 nd-app 目录下执行（及IDEA运行时的Work目录）
         *  1）开发路径：src/main/resources/
         *  2）生产路径：target/（运行的PWD路径）
         *  如果此处使用开发路径会出现无法导入数据情况，而开发时的路径只有在特殊场景使用
         *  - 直接使用 IDEA 运行此程序
         */
        final String vPath = Ut.ioPath(path, input.environment());
        LOG.Ke.info(clazz, """
                信息说明
                \t环境：{0}, 开启OOB：{1}
                \t数据路径：{2}
                \t过滤模式：{3}""",
            input.environment(), oob, vPath, prefix
        );


        // 构造启动器（构造命令启动器）
        final KLauncher<Vertx> container = KLauncher.create(clazz, args);
        container.start(Electy.whenInstruction((vertx, config) -> {

        }));
    }
}
