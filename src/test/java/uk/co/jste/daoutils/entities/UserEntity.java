package uk.co.jste.daoutils.entities;

import java.io.Serializable;
import uk.co.jste.daoutils.entity.Persistable;
import uk.co.jste.daoutils.entity.TemporalEntity;
import uk.co.jste.daoutils.types.EffectivityTimestampInterval;

public interface UserEntity<I extends Serializable> extends Persistable<I>,TemporalEntity<EffectivityTimestampInterval> {

  String getFirstName();

  void setFirstName(String firstName);

  String getSecondName();

  void setSecondName(String secondName);
}
