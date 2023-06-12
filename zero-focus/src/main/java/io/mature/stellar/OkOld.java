package io.mature.stellar;

import io.horizon.atom.datamation.KFabric;
import io.horizon.uca.log.Annal;
import io.macrocosm.specification.program.HArk;
import io.mature.stellar.vendor.OkB;
import io.modello.atom.app.KGlobal;
import io.modello.atom.app.KIntegration;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.mod.atom.error._417DataAtomNullException;
import io.vertx.mod.atom.modeling.builtin.DataAtom;
import io.vertx.mod.ke.refine.Ke;
import io.vertx.up.eon.KName;
import io.vertx.up.unity.Ux;
import io.vertx.up.util.Ut;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="http://www.origin-x.cn">Lang</a>
 */

@Deprecated
public class OkOld implements OkA {
    private static final Annal LOGGER = Annal.get(OkOld.class);
    private static OkOld INSTANCE;

    private transient final ConcurrentMap<String, OkB> vendors = new ConcurrentHashMap<>();
    private final transient KGlobal tenant;
    /*
     * 解析结果
     */
    private transient HArk app;
    private transient boolean initialized = false;

    private OkOld(final JsonObject tenantData) {
        this.tenant = Ut.deserialize(tenantData, KGlobal.class);
        this.tenant.vendors().forEach(name -> {
            LOGGER.info("[ OkOld ] Vendor {0} has been created!", name);
            final KIntegration integration = this.tenant.integration(name);
            this.vendors.put(name, OkB.of(this, integration));
        });
    }

    // --------------- 静态公开API ---------------
    public static void on(final Handler<AsyncResult<OkOld>> handler) {
        final OkOld ok = configure();
        /*
         * 旧代码
         * handler.handle(ok.initializeAmbient())
         * 顺序不对，应该是future执行完成后让handler捕捉onSuccess的结果，而不是
         * 在此处直接调用 handler.handle 前一个结果，此处 handler 要等待 Future<OkOld> 执行
         * 完成
         */
        final Future<OkOld> future = ok.initializeAmbient();
        future.onComplete(res -> {
            if (res.succeeded()) {
                handler.handle(Future.succeededFuture(res.result()));
            } else {
                if (Objects.nonNull(res.cause())) {
                    res.cause().printStackTrace();
                }
            }
        });
    }

    /**
     * 「Async」根据传入的模型定义对象构造对应的字典翻译器。
     */
    public static Future<KFabric> fabric(final DataAtom atom, final String bName) {
        if (Objects.isNull(atom)) {
            return Future.failedFuture(new _417DataAtomNullException(OkOld.class));
        } else {
            return ok().compose(initialized -> {
                final OkB partyB = initialized.partyB(bName);
                return partyB.fabric(atom.identifier()).compose(fabric -> {
                    fabric.mapping().bind(atom.type());
                    return Ux.future(fabric);
                });
            });
        }
    }

    public static Future<HArk> app() {
        return ok().compose(initialized -> Ux.future(initialized.configApp()));
    }

    public static Future<OkB> vendor(final String name) {
        return ok().compose(initialized -> Ux.future(initialized.partyB(name)));
    }

    public static Future<OkOld> ok() {
        final OkOld ok = configure();
        final Future<OkOld> future;
        if (ok.initialized) {
            future = Ux.future(ok);
        } else {
            future = ok.initializeAmbient();
        }
        return future;
    }

    // --------------- 环境初始化专用 ---------------
    /*
     * 第一步：Ok初始化专用
     */
    private static OkOld configure() {
        if (Objects.isNull(INSTANCE)) {
            // 新版 ArgoStore.stellar()
            final JsonObject data = ArgoStore.stellar();
            INSTANCE = new OkOld(data);
        }
        return INSTANCE;
    }

    /*
     * 第二步：环境初始化，替换原始的 Uquip.on，并带异步回调
     */
    private Future<OkOld> initializeAmbient() {
        final Vertx vertx = Ux.nativeVertx();
        if (!this.initialized) {
            OInfix.on(vertx);
            LOGGER.info("[ OkOld ] Zero Infusion has been initialized!! = {0}", this.tenant);
            // 应用初始化
            final JsonObject app = this.tenant.getApplication();
            final String sigma = app.getString(KName.SIGMA);
            final String appId = app.getString(KName.APP_ID);
            if (Ut.isNil(appId)) {
                this.app = Ke.ark(sigma);
            } else {
                this.app = Ke.ark(appId);
            }
            // 环境初始化完成
            LOGGER.info("[ OkOld ] Tenant AmbientOld has been initialized!! = {0}", this.tenant);
            this.initialized = true;
            return Future.succeededFuture(this);
        } else {
            return Future.succeededFuture(this);
        }
    }

    // --------------- 专用API ---------------
    @Override
    public boolean initialized() {
        return this.initialized;
    }

    @Override
    public KGlobal partyA() {
        return this.tenant;
    }

    @Override
    public OkB partyB(final String name) {
        return this.vendors.get(name);
    }

    @Override
    public HArk configApp() {
        return this.app;
    }
    // --------------- 静态API，默认模式 ---------------
}
