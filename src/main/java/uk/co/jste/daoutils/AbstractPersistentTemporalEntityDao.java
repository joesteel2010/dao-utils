package uk.co.jste.daoutils;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import uk.co.jste.daoutils.entity.Persistable;
import uk.co.jste.daoutils.temporal.DateTimeEffectivityAware;

@SuppressWarnings({"unused", "unchecked", "WeakerAccess", "squid:S1905"})
public abstract class AbstractPersistentTemporalEntityDao<I extends Serializable,T extends Persistable<I>> extends
    AbstractPersistentEntityDao<T, I> {

  public static final String EFFECTIVITY_FILTER = "effectivityFilter";
  public static final String EFFECTIVITY_PARAMETER = "effective";
  public static final String EFFECTIVITY_TYPE = "timestamp";

  public AbstractPersistentTemporalEntityDao(EntityManager entityManager) {
    super(entityManager);
  }

  @DateTimeEffectivityAware
  public <S extends T> Optional<S> getActive(I id, Class<S> targetEntityClass) {
    return super.get(id, targetEntityClass);
  }

  @DateTimeEffectivityAware
  public Optional<T> getActive(I id) {
    return super.get(id);
  }

  @DateTimeEffectivityAware
  public List<T> getAllActive(Class<T> targetEntityClass) {
    return super.getAll(targetEntityClass);
  }

  @DateTimeEffectivityAware
  public List<T> getAllActive() {
    return super.getAll();
  }

  @DateTimeEffectivityAware
  public long countAllActive() {
    return super.countAll();
  }

  @DateTimeEffectivityAware
  public <S extends T> long countAllActive(Class<S> targetEntityClass) {
    return super.countAll(targetEntityClass);
  }
}
