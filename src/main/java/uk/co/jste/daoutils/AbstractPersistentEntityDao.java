package uk.co.jste.daoutils;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import uk.co.jste.daoutils.entity.Persistable;

@SuppressWarnings({"unused", "unchecked", "WeakerAccess", "squid:S1905"})
public abstract class AbstractPersistentEntityDao<T extends Persistable<I>, I extends Serializable> implements PersistentEntityDAO<T, I> {

  private final EntityManager entityManager;

  public AbstractPersistentEntityDao(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  protected EntityManager getEntityManager() {
    return entityManager;
  }

  protected Session getSession() {
    return entityManager.unwrap(Session.class);
  }

  @Override
  public <S extends T> Optional<S> get(I id, Class<S> targetEntityClass) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<S> criteria = builder.createQuery(targetEntityClass);
    Root<S> root = criteria.from(targetEntityClass);
    criteria.where(builder.equal(root.get("id"), id));
    List<S> resultList = getSession().createQuery(criteria).getResultList();
    if (resultList.size() > 1) {
      throw new NonUniqueResultException();
    }
    return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
  }

  @Override
  public Optional<T> get(I id) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<T> criteria = builder.createQuery(getEntityClass());
    Root<T> root = criteria.from(getEntityClass());
    criteria.where(builder.equal(root.get("id"), id));
    List<T> resultList = getSession().createQuery(criteria).getResultList();
    if (resultList.size() > 1) {
      throw new NonUniqueResultException();
    }
    return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
  }

  @Override
  public <S extends T> List<S> getAll(Class<S> targetEntityClass) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<S> criteria = builder.createQuery(targetEntityClass);
    criteria.from(targetEntityClass);
    return getSession().createQuery(criteria).getResultList();
  }

  @Override
  public List<T> getAll() {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<T> criteria = builder.createQuery(getEntityClass());
    criteria.from(getEntityClass());
    return getSession().createQuery(criteria).getResultList();
  }

  @Override
  public <S extends T> S saveOrUpdate(S entity) {
    getSession().saveOrUpdate(entity);
    return (S) getSession().merge(entity);
  }

  @Override
  public void delete(T entity) {
    getSession().delete(entity);
  }

  @Override
  public <S extends T> void delete(I id, Class<S> targetEntityClass) {
    delete(get(id, targetEntityClass).orElseThrow(RuntimeException::new));
  }

  @Override
  public void delete(I id) {
    delete(id, getEntityClass());
  }

  @Override
  public <S extends T> int deleteAll(Class<S> targetEntityClass) {
    List<S> instancesToDelete = getAll(targetEntityClass);

    for (S instance : instancesToDelete) {
      delete(instance);
    }

    return instancesToDelete.size();
  }

  @Override
  public int deleteAll() {
    return deleteAll(getEntityClass());
  }

  @Override
  public void refresh(T entity) {
    getSession().refresh(entity);
  }

  @Override
  public <S extends T> long countAll(Class<S> targetEntityClass) {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<Long> cqCount = builder.createQuery(Long.class);
    Root<S> entityRoot = cqCount.from(targetEntityClass);
    cqCount.select(builder.count(entityRoot));
    return entityManager.createQuery(cqCount).getSingleResult();
  }

  @Override
  public long countAll() {
    CriteriaBuilder builder = getSession().getCriteriaBuilder();
    CriteriaQuery<Long> cqCount = builder.createQuery(Long.class);
    Root<T> entityRoot = cqCount.from(getEntityClass());
    cqCount.select(builder.count(entityRoot));
    return entityManager.createQuery(cqCount).getSingleResult();
  }
}
