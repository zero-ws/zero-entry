package io.mature.extension.uca.console;

import io.mature.extension.scaffold.console.AbstractInstruction;
import io.vertx.boot.supply.DataImport;
import io.vertx.core.Future;
import io.vertx.up.plugin.shell.atom.CommandInput;
import io.vertx.up.plugin.shell.cv.em.TermStatus;
import io.vertx.up.plugin.shell.refine.Sl;
import io.vertx.up.unity.Ux;

import java.util.Objects;

/**
 * @author <a href="http://www.origin-x.cn">Lang</a>
 */
public class LoadInstruction extends AbstractInstruction {
    @Override
    public Future<TermStatus> executeAsync(final CommandInput args) {
        final Boolean isOob = this.inBoolean(args, "o");
        // Fix Issue
        final boolean oob;
        if (Objects.isNull(isOob)) {
            oob = false;
        } else {
            oob = isOob;
        }
        return this.partyA().compose(ok -> {
            final DataImport importer = DataImport.of();
            if (oob) {
                return importer.landAsync("init/oob/");
            } else {
                return importer.loadAsync("init/oob");
            }
        }).compose(done -> {
            Sl.output("您的元数据仓库已重置初始化完成！重置结果：{0}", done);
            return Ux.future(done ? TermStatus.SUCCESS : TermStatus.FAILURE);
        });
    }
}
