package uk.co.jste.daoutils.entity;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;
import org.hibernate.annotations.OptimisticLocking;

@OptimisticLocking
@SuppressWarnings("unused")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class MutablePersistentEntity extends PersistentEntity<Long> {

  private static final long serialVersionUID = 8208651173275218326L;

  @Version
  private Integer version;

  public Integer getVersion() {
    return version;
  }

  protected void setVersion(Integer version) {
    this.version = version;
  }
}
