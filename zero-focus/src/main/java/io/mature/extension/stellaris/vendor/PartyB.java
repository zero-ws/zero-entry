package io.mature.extension.stellaris.vendor;

import io.horizon.atom.datamation.KDictConfig;
import io.horizon.atom.datamation.KMap;
import io.horizon.spi.component.ExAmbientDictionary;
import io.mature.extension.stellaris.OkA;
import io.modello.atom.app.KIntegration;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.unity.Ux;
import io.vertx.up.util.Ut;

/**
 * ## 默认组装器实现
 *
 * ### 1. 基本介绍
 *
 * 系统默认组装器，根据运行目录构造UCMDB的专用配置。
 *
 * ### 2. 目录规范
 *
 * 一次配置组装器的配置规范如下：
 *
 * |文件名|含义|
 * |:---|:---|
 * |`[folder]/cmdb-v2/dict-config/[filename].json`|字典定义配置。|
 * |`[folder]/cmdb-v2/dict-epsilon/[filename].json`|字典消费配置。|
 * |`[folder]/cmdb-v2/mapping/[filename].json`|字段映射器配置。|
 * |`[folder]/cmdb-v2/options/[filename].json`|通道专用服务配置，对应`I_SERVICE`中的serviceConfig，构造options。|
 *
 * @author <a href="http://www.origin-x.cn">Lang</a>
 */
class PartyB extends AbstractParty {
    private static final String ROOT = "runtime/";
    /**
     * {@link KDictConfig}对象，字典翻译器配置。
     */
    private final transient KDictConfig dict;
    /**
     * {@link KMap}对象，字段映射器配置。
     */
    private final transient KMap mapping;
    /**
     * {@link JsonObject}对象，服务配置，对应`ServiceConfig`字段，构造`options`。
     */
    private final transient JsonObject options;
    private final transient KIntegration integration;

    /*
     * 构造函数，CMDB专用配置组装器，配置根目录为`src/main/resources`目录，待使用工厂模式。
     */
    PartyB(final OkA partyA, final KIntegration integration) {
        super(partyA);
        this.integration = integration;
        /*
         * Dict Config
         */
        final String dict = this.buildPath("dict-config", integration);
        final String epsilon = this.buildPath("dict-epsilon", integration);
        this.dict = new KDictConfig(Ut.ioJArray(dict))
            .bind(Ux.dictEpsilon(Ut.ioJObject(epsilon)))
            .bind(ExAmbientDictionary.class);
        /*
         * Mapping
         */
        final String mapping = this.buildPath("mapping", integration);
        this.mapping = new KMap(Ut.ioJObject(mapping));
        /*
         * Options
         */
        final String options = this.buildPath("options", integration);
        this.options = Ut.ioJObject(options);
    }

    private String buildPath(final String key, final KIntegration integration) {
        final String hit = key.replace("/", "");
        return ROOT + integration.getVendorConfig() + "/" + hit + "/" + integration.getVendor() + ".json";
    }

    @Override
    public KIntegration configIntegration() {
        return this.integration;
    }

    /**
     * 对应通道定义`I_SERVICE`中的`serviceConfig`属性，服务配置，构造`options`专用
     *
     * @return {@link JsonObject}
     */
    @Override
    public JsonObject configService() {
        final JsonObject params = this.inputData();
        this.options.mergeIn(params, true);
        return this.options;
    }

    /**
     * 构造映射配置对象，专用执行字段映射处理。
     *
     * @return {@link KMap}
     */
    @Override
    public KMap mapping() {
        return this.mapping;
    }

    /**
     * 构造字典翻译器专用配置
     *
     * @return {@link Future}<{@link KDictConfig}>
     */
    @Override
    protected Future<KDictConfig> configDict() {
        return Ux.future(this.dict);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <CHILD extends OkB> CHILD copy() {
        final PartyB okB = new PartyB(this.partyA, this.integration.copy());
        okB.options.mergeIn(this.options.copy());
        return (CHILD) okB;
    }
}
