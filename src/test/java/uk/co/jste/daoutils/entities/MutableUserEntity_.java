package uk.co.jste.daoutils.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.co.jste.daoutils.types.EffectivityTimestampInterval;

@StaticMetamodel(ImmutableUserEntity.class)
public class MutableUserEntity_ {
  public static volatile SingularAttribute<ImmutableUserEntity, Long> id;
  public static volatile SingularAttribute<ImmutableUserEntity, String> firstName;
  public static volatile SingularAttribute<ImmutableUserEntity, String> secondName;
  public static volatile SingularAttribute<ImmutableUserEntity, EffectivityTimestampInterval> active;
}