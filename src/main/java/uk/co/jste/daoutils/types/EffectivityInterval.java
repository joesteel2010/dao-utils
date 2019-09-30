package uk.co.jste.daoutils.types;

import java.io.Serializable;

public interface EffectivityInterval<T extends Comparable> extends Serializable {

  T getStart();

  T getEnd();
}
