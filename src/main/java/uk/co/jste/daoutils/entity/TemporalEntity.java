package uk.co.jste.daoutils.entity;

import uk.co.jste.daoutils.types.EffectivityInterval;

public interface TemporalEntity<T extends EffectivityInterval> {

  T getEffective();
}
