package uk.co.jste.daoutils.entity;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;
import uk.co.jste.daoutils.types.EffectivityTimestampInterval;

@MappedSuperclass
public abstract class ImmutablePersistentTimestampTemporalEntity<I extends Serializable> extends
    ImmutablePersistentTemporalEntity<I> implements TemporalEntity<EffectivityTimestampInterval> {

  private static final long serialVersionUID = 1105160824059741069L;
}
