package uk.co.jste.daoutils.types;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jste.daoutils.entities.ImmutableUserEntity;
import uk.co.jste.daoutils.repositories.ImmutableUserRepository;

@DataJpaTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = uk.co.jste.daoutils.config.Configuration.class)
public class EffectivityIntervalTypeTest {
  private ImmutableUserEntity immutableUserEntity;
  private final LocalDateTime userStartDate = LocalDateTime.of(2000, 1, 1, 12, 0, 0);
  private final LocalDateTime userEndDate = LocalDateTime.of(2020, 1, 1, 12, 0, 0);

  private ImmutableUserRepository immutableUserRepository;
  private EntityManager entityManager;

  @Before
  public void setUp() {
    immutableUserEntity = new ImmutableUserEntity();
    immutableUserEntity.setFirstName("John");
    immutableUserEntity.setSecondName("Doe");
    immutableUserEntity.setEffective(new EffectivityTimestampInterval(userStartDate, userEndDate));
  }

  @Test
  public void testRepositoryLoads() {
    assertNotNull(immutableUserRepository);
  }

  @Test
  public void testRepositorySave() {
    immutableUserRepository.save(immutableUserEntity);
    entityManager.flush();
  }

  @Test
  public void testRepositorySaveWithNullStart() {
    immutableUserEntity.setEffective(new EffectivityTimestampInterval(null, userEndDate));
    immutableUserRepository.save(immutableUserEntity);
    entityManager.flush();
  }

  @Test
  public void testRepositorySaveWithNullEnd() {
    immutableUserEntity.setEffective(new EffectivityTimestampInterval(userStartDate, null));
    immutableUserRepository.save(immutableUserEntity);
    entityManager.flush();
  }

  @Test
  @Sql("/temporal-user.sql")
  public void testDeleteByID() {
    immutableUserRepository.deleteById(100L);
    entityManager.flush();
  }

  @Test
  @Sql("/temporal-user.sql")
  public void testFindByID() {
    Optional<ImmutableUserEntity> userOptional = immutableUserRepository.findById(100L);
    assertTrue(userOptional.isPresent());
    assertThat(userOptional.get().getFirstName(), is("John"));
    assertThat(userOptional.get().getSecondName(), is("Doe"));
    assertThat(userOptional.get().getEffective().getStart(), is(userStartDate));
    assertThat(userOptional.get().getEffective().getEnd(), is(userEndDate));
  }

  @Test
  @Sql("/temporal-user.sql")
  public void testFindByIDWithNullStart() {
    Optional<ImmutableUserEntity> userOptional = immutableUserRepository.findById(101L);
    assertTrue(userOptional.isPresent());
    assertThat(userOptional.get().getFirstName(), is("Jane"));
    assertThat(userOptional.get().getSecondName(), is("Doe"));
    assertThat(userOptional.get().getEffective().getStart(), is(nullValue()));
    assertThat(userOptional.get().getEffective().getEnd(), is(userEndDate));
  }

  @Test
  @Sql("/temporal-user.sql")
  public void testFindByIDWithNullEnd() {
    Optional<ImmutableUserEntity> userOptional = immutableUserRepository.findById(102L);
    assertTrue(userOptional.isPresent());
    assertThat(userOptional.get().getFirstName(), is("Joe"));
    assertThat(userOptional.get().getSecondName(), is("Doe"));
    assertThat(userOptional.get().getEffective().getStart(), is(userStartDate));
    assertThat(userOptional.get().getEffective().getEnd(), is(nullValue()));
  }

  @Autowired
  public void setImmutableUserRepository(ImmutableUserRepository immutableUserRepository) {
    this.immutableUserRepository = immutableUserRepository;
  }

  @Autowired
  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }
}