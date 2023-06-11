package io.mature.extension.stellaris;

import io.mature.extension.stellaris.vendor.OkB;
import io.modello.atom.app.KGlobal;

/**
 * @author <a href="http://www.origin-x.cn">Lang</a>
 */
public interface OkA extends OkX {

    boolean initialized();

    KGlobal partyA();

    OkB partyB(String name);
}
