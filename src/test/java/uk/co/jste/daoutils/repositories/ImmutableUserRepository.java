package uk.co.jste.daoutils.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.jste.daoutils.entities.ImmutableUserEntity;

@Repository
public interface ImmutableUserRepository extends CrudRepository<ImmutableUserEntity, Long> {

}
