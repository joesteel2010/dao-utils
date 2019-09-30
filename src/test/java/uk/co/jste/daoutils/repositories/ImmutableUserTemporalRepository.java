package uk.co.jste.daoutils.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.jste.daoutils.entities.ImmutableUserEntity;
import uk.co.jste.daoutils.temporal.DateTimeEffectivityAware;

@Repository
public interface ImmutableUserTemporalRepository extends CrudRepository<ImmutableUserEntity, Long> {
  @Override
  @DateTimeEffectivityAware
  Iterable<ImmutableUserEntity> findAll();
}
