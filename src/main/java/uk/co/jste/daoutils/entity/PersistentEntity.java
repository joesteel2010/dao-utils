package uk.co.jste.daoutils.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base class for persistent entities managed within the JPA persistence context.
 *
 * <p>Note that this class does NOT provide any special {@link #hashCode()} and {@link
 * #equals(Object)} implementation - this is a task left to be done, depending on the chosen method
 * implementation pattern.
 *
 * <p>This class implicitly supports the {@link Object#clone() clone} operation. Subclasses can
 * implement {@link Cloneable} interface if they wish to add explicit <tt>clone</tt> operation
 * support.
 *
 * @param <I> Java type of the primary key column.
 * @see Persistable
 */
@SuppressWarnings("unused")
@MappedSuperclass
public abstract class PersistentEntity<I extends Serializable> implements Persistable<I> {

  private static final long serialVersionUID = -3831662382201152789L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "defaultGenerator")
  protected I id;

  public I getId() {
    return id;
  }

  protected void setId(I id) {
    this.id = id;
  }
}
