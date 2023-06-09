package io.mature.boot.supply;

import io.macrocosm.specification.app.HApp;
import io.macrocosm.specification.program.HArk;
import io.mature.stellar.owner.OkA;
import io.modello.atom.app.KGlobal;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.up.eon.KName;

import java.util.Objects;

/**
 * @author lang : 2023-06-11
 */
class EInput {

    static MultiMap input(final OkA partyA, final String identifier) {
        final MultiMap params = MultiMap.caseInsensitiveMultiMap();
        params.add(KName.IDENTIFIER, identifier);
        /*
         * 应用处理
         */
        final JsonObject data = inputQr(partyA);
        params.add(KName.SIGMA, data.getString(KName.SIGMA));
        params.add(KName.APP_ID, data.getString(KName.APP_ID));
        return params;
    }

    static JsonObject inputQr(final OkA partyA) {
        final JsonObject params = new JsonObject();
        final HArk ark = partyA.configArk();
        if (Objects.isNull(ark)) {
            final KGlobal tenant = partyA.partyA();
            final JsonObject application = tenant.getApplication();
            params.put(KName.SIGMA, application.getString(KName.SIGMA));
            params.put(KName.APP_ID, application.getString(KName.APP_ID));
        } else {
            final HApp app = ark.app();
            params.put(KName.APP_ID, app.option(KName.APP_ID));
            params.put(KName.SIGMA, app.option(KName.SIGMA));
        }
        return params;
    }
}
