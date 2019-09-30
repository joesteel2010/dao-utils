package uk.co.jste.daoutils.entities;

import static uk.co.jste.daoutils.AbstractPersistentTemporalEntityDao.EFFECTIVITY_FILTER;

import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Filter;
import uk.co.jste.daoutils.entity.ImmutablePersistentTimestampTemporalEntity;
import uk.co.jste.daoutils.entity.MutablePersistentTimestampTemporalEntity;

@Entity
@Table(name = "USER")
@AttributeOverride(name="id", column = @Column(name="USER_ID"))
@Filter(name=EFFECTIVITY_FILTER, condition=":effective between start and end")
public class MutableUserEntity extends MutablePersistentTimestampTemporalEntity implements UserEntity<Long> {

  private static final long serialVersionUID = 843453303283850791L;

  private String firstName;
  private String secondName;

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public String getSecondName() {
    return secondName;
  }

  @Override
  public void setSecondName(String secondName) {
    this.secondName = secondName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MutableUserEntity that = (MutableUserEntity) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
