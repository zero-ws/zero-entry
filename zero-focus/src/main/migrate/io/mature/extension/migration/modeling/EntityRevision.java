package io.mature.extension.migration.modeling;

import cn.vertxup.atom.domain.tables.pojos.MEntity;

public class EntityRevision extends AbstractRevision {

    public EntityRevision() {
        super(MEntity.class);
    }
}
