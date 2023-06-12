package io.mature.extension.quiz.develop;

import io.horizon.atom.program.KPathAtom;
import io.horizon.eon.VSpec;
import io.horizon.eon.VString;
import io.horizon.eon.em.Environment;
import io.horizon.uca.cache.Cc;
import io.horizon.uca.log.Annal;
import io.mature.extension.quiz.oclick.InstClick;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.mod.ke.booter.Bt;
import io.vertx.up.plugin.jooq.JooqInfix;
import io.vertx.up.unity.Ux;
import io.vertx.up.util.Ut;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import static io.vertx.mod.ke.refine.Ke.LOG;

/**
 * @author <a href="http://www.origin-x.cn">Lang</a>
 */
public class DevKit {
    private static final KPathAtom cmdb = KPathAtom.of("cmdb").bind(Environment.Development);
    private static final Cc<String, DevModeller> CC_MODELLER = Cc.openThread();

    static {
        JooqInfix.init(Ux.nativeVertx());
    }

    // ----------------------- DevMenu -------------------------
    public static Future<JsonArray> menuFetch() {
        return menuFetch(true);
    }

    public static Future<JsonArray> menuFetch(final boolean readable) {
        return DevMenu.menuFetch(null, readable);
    }

    public static Future<ConcurrentMap<String, JsonArray>> menuBoot() {
        return DevMenu.menuInitialize(roles());
    }

    public static Future<Boolean> menuOutput(final ConcurrentMap<String, JsonArray> menuMap, final String root) {
        return DevMenu.menuOutput(menuMap, root);
    }

    // ----------------------- Dev Data Loading -------------------------
    private static Set<String> roles() {
        final List<String> files = Ut.ioFiles(VSpec.Web.init.permission.ui_menu.ROLE);
        final Set<String> roleSet = new HashSet<>();
        files.forEach(file -> {
            final String role = file.replace(".json", VString.EMPTY);
            roleSet.add(role);
        });
        return roleSet;
    }

    /*
     * All the methods of this part is standalone, it will ignore OOB folder
     *
     * isOob = false
     */
    // CMDB
    public static void oobCmdb() {
        doLoading(cmdb.path(), null, false);
    }

    // CAB
    public static void oobCab() {
        doLoading(VSpec.Web.init.oob.CAB, null, false);
    }

    // DATA
    public static void oobData() {
        doLoading(VSpec.Web.init.oob.DATA, null, false);
    }

    // ENVIRONMENT
    public static void oobEnvironment() {
        doLoading(VSpec.Web.init.oob.ENVIRONMENT, null, false);
    }

    // MODULAT
    public static void oobModulat() {
        doLoading(VSpec.Web.init.oob.MODULAT, null, false);
    }

    // INTEGRATION
    public static void oobIntegration() {
        doLoading(VSpec.Web.init.oob.INTEGRATION, null, false);
    }

    // WORKFLOW
    public static void oobRule() {
        doLoading(VSpec.Web.init.oob.WORKFLOW, null, false);
    }

    public static void oobRule(final String workflow) {
        doLoading(VSpec.Web.init.oob.workflow.of(workflow), null, false);
    }

    // ROLE
    public static void oobRole(final String role) {
        doLoading(VSpec.Web.init.oob.role.of(role), null, false);
    }

    // CAB/UI FOR MODEL
    public static void oobUi(final String identifier) {
        doLoading(cmdb.ui(identifier), null, false);
    }

    public static void oobUi(final String identifier, final String prefix) {
        doLoading(cmdb.ui(identifier), prefix, false);
    }

    // ----------------------- Data Loading for Initializing -------------------------
    /*
     * Following two APIs are related to standard data loading of oob, the default actions are:
     *
     * - oobLoader()
     *
     * When you start this loader, zero extension framework will load the data into database to do initializing
     * on the empty database here.
     */
    public static void oobLoader(final String prefix) {
        doLoading(VSpec.Web.init.OOB, prefix, true);
    }

    public static void oobLoader() {
        doLoading(VSpec.Web.init.OOB, null, true);
    }

    public static void oobLoader(final String path, final boolean isOob) {
        doLoading(path, null, isOob);
    }

    // ----------------------- DevModeller Object -------------------------

    public static DevModeller modeller() {
        return modeller(cmdb.input(), cmdb.output());
    }

    public static DevModeller modeller(final String input, final String output) {
        Objects.requireNonNull(input, output);
        final String hashKey = Ut.encryptMD5(input + VString.COLON + output);
        return CC_MODELLER.pick(() -> new DevModeller(input, output), hashKey);
    }

    // ----------------------- Inst Method -------------------------
    public static void instLoad(final Class<?> target, final String... args) {
        final InstClick click = InstClick.instance(target);
        click.runLoad(args);
    }

    public static void instAtom(final Class<?> target, final String... args) {
        final InstClick click = InstClick.instance(target);
        click.runAtom(args);
    }

    public static void instMenu(final Class<?> target, final String... args) {
        final InstClick click = InstClick.instance(target);
        click.runMenu(args);
    }

    // ----------------------- Private Method -------------------------
    @SuppressWarnings("all")
    private static void doLoading(final String root, final String prefix, final boolean isOob) {
        final Annal logger = Annal.get(DevKit.class);
        LOG.Ke.info(logger, "Data Loading from `{0}`", root);
        Bt.init(root, Objects.isNull(prefix) ? VString.EMPTY : prefix, isOob);
    }
}
