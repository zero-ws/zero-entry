package io.mature.stellar;

import io.horizon.eon.spec.VWeb;
import io.mature.stellar.vendor.OkB;
import io.modello.atom.app.KGlobal;

/**
 * @author <a href="http://www.origin-x.cn">Lang</a>
 */
public interface OkA extends Party {
    /**
     * 当前甲方环境已经初始化完成
     *
     * @return 是否初始化完成
     */
    boolean initialized();

    /**
     * 对应文件目录 {@link VWeb.runtime#CONFIGURATION_JSON}
     *
     * @return {@link KGlobal}
     */
    KGlobal partyA();

    /**
     * 和当前甲方环境相关的所有乙方环境加载
     *
     * @param name 乙方环境名称
     *
     * @return {@link OkB}
     */
    OkB partyB(String name);
}