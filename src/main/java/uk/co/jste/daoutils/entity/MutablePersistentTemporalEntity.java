package uk.co.jste.daoutils.entity;

import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.OptimisticLocking;
import uk.co.jste.daoutils.types.EffectivityTimestampInterval;

@OptimisticLocking
@MappedSuperclass
@SuppressWarnings("unused")
public abstract class MutablePersistentTemporalEntity extends MutablePersistentEntity implements
    TemporalEntity<EffectivityTimestampInterval> {

  private static final long serialVersionUID = 781660032512772758L;
  private EffectivityTimestampInterval effective;

  public EffectivityTimestampInterval getEffective() {
    return effective;
  }

  public void setEffective(EffectivityTimestampInterval effective) {
    this.effective = effective;
  }
}
