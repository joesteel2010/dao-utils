package uk.co.jste.daoutils.entity;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.Immutable;

@Immutable
@MappedSuperclass
public abstract class ImmutablePersistentEntity<I extends Serializable> extends PersistentEntity<I> {

  private static final long serialVersionUID = -8938621442547726985L;
}
