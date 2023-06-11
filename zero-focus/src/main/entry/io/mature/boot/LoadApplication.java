package io.mature.boot;

import io.horizon.eon.em.Environment;
import io.horizon.exception.web._400BadRequestException;
import io.horizon.uca.boot.KLauncher;
import io.vertx.boot.supply.Electy;
import io.vertx.core.Vertx;
import io.vertx.up.util.Ut;

import static io.vertx.mod.ke.refine.Ke.LOG;

/**
 * @author lang : 2023-06-10
 */
public class LoadApplication {

    public static void run(final Class<?> clazz, final String... args) {
        if (1 > args.length) {
            // 无参数不可执行 HExtension
            throw new _400BadRequestException(clazz);
        }

        // 参数数量必须 > 1
        //   0 - inputPath,
        //   1 - prefix
        //   2 - oob
        final boolean isOob = Boolean.parseBoolean(args[0]);
        final String inputPath = 1 == args.length || Ut.isNil(args[1])
            ? "init/oob" : args[1];
        /*
         *  路径构造，此处路径构造必须是 Production
         *  由于 inst 工具构造的所有信息都是在 nd-app 目录下执行（及IDEA运行时的Work目录）
         *  1）开发路径：src/main/resources/
         *  2）生产路径：target/（运行的PWD路径）
         *  如果此处使用开发路径会出现无法导入数据情况
         */
        final String path = Ut.ioPath(inputPath, Environment.Production);
        LOG.Ke.info(clazz, "加载路径：{0}, 开启OOB：{1}", inputPath, isOob);
        // 构造启动器
        final KLauncher<Vertx> container = KLauncher.create(clazz, args);
        container.start(Electy.whenInstruction((vertx, config) -> {

        }));
    }
}
