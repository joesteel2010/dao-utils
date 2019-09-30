package uk.co.jste.daoutils.entity;

import javax.persistence.MappedSuperclass;
import uk.co.jste.daoutils.types.EffectivityTimestampInterval;

@MappedSuperclass
public abstract class MutablePersistentTimestampTemporalEntity extends
    MutablePersistentTemporalEntity implements TemporalEntity<EffectivityTimestampInterval> {

  private static final long serialVersionUID = 5774838272066392529L;
}
