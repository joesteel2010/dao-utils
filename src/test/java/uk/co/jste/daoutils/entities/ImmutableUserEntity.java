package uk.co.jste.daoutils.entities;

import static uk.co.jste.daoutils.AbstractPersistentTemporalEntityDao.EFFECTIVITY_FILTER;

import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Filter;
import uk.co.jste.daoutils.entity.ImmutablePersistentTimestampTemporalEntity;

@Entity
@Table(name = "USER")
@AttributeOverride(name="id", column = @Column(name="USER_ID"))
@Filter(name=EFFECTIVITY_FILTER, condition=":effective between start and end")
public class ImmutableUserEntity extends ImmutablePersistentTimestampTemporalEntity<Long> implements UserEntity<Long> {

  private static final long serialVersionUID = 843453303283850791L;

  private String firstName;
  private String secondName;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getSecondName() {
    return secondName;
  }

  public void setSecondName(String secondName) {
    this.secondName = secondName;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ImmutableUserEntity{");
    sb.append("firstName='").append(firstName).append('\'');
    sb.append(", secondName='").append(secondName).append('\'');
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImmutableUserEntity that = (ImmutableUserEntity) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
