package uk.co.jste.daoutils.entity;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.Immutable;
import uk.co.jste.daoutils.types.EffectivityTimestampInterval;

@Immutable
@MappedSuperclass
public abstract class ImmutablePersistentTemporalEntity<I extends Serializable> extends ImmutablePersistentEntity<I> implements
    TemporalEntity<EffectivityTimestampInterval> {

  private static final long serialVersionUID = 5017661172368927320L;
  private EffectivityTimestampInterval effective;

  public EffectivityTimestampInterval getEffective() {
    return effective;
  }

  public void setEffective(EffectivityTimestampInterval effective) {
    this.effective = effective;
  }
}
