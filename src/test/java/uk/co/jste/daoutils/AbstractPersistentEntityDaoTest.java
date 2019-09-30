package uk.co.jste.daoutils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jste.daoutils.AbstractPersistentTemporalEntityDao;
import uk.co.jste.daoutils.entities.ImmutableUserEntity;

@DataJpaTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {uk.co.jste.daoutils.config.Configuration.class})
public class AbstractPersistentEntityDaoTest {
  private AbstractPersistentTemporalEntityDao<Long,ImmutableUserEntity> userDao;

  private final LocalDateTime userStartDate = LocalDateTime.of(2000, 1, 1, 12, 0, 0);
  private final LocalDateTime userEndDate = LocalDateTime.of(2020, 1, 1, 12, 0, 0);

  @Test
  public void getEntityManager() {
    assertNotNull(userDao.getEntityManager());
  }

  @Test
  public void getSession() {
    assertNotNull(userDao.getSession());
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void get() {
    Optional<ImmutableUserEntity> user = userDao.get(100L);
    assertTrue(user.isPresent());
    assertThat(user.get().getFirstName(), is("John"));
    assertThat(user.get().getSecondName(), is("Doe"));
    assertThat(user.get().getEffective().getStart(), is(userStartDate));
    assertThat(user.get().getEffective().getEnd(), is(userEndDate));
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void getWithClass() {
    Optional<ImmutableUserEntity> user = userDao.get(100L, ImmutableUserEntity.class);
    assertTrue(user.isPresent());
    assertThat(user.get().getFirstName(), is("John"));
    assertThat(user.get().getSecondName(), is("Doe"));
    assertThat(user.get().getEffective().getStart(), is(userStartDate));
    assertThat(user.get().getEffective().getEnd(), is(userEndDate));
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void getAll() {
    List<ImmutableUserEntity> users = userDao.getAll();
    assertThat(users, hasSize(3));
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void getAllWithClass() {
    List<ImmutableUserEntity> users = userDao.getAll(ImmutableUserEntity.class);
    assertThat(users, hasSize(3));
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void saveOrUpdateSaves() {
    ImmutableUserEntity user = new ImmutableUserEntity();
    user.setFirstName("Chuck");
    user.setSecondName("Norris");
    userDao.saveOrUpdate(user);
    userDao.getEntityManager().flush();
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void saveOrUpdateUpdates() {
    Optional<ImmutableUserEntity> user = userDao.get(100L);
    assertTrue(user.isPresent());
    user.get().setFirstName("Chuck");
    user.get().setSecondName("Norris");
    userDao.saveOrUpdate(user.get());
    userDao.getEntityManager().flush();
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void deleteByID() {
    userDao.delete(100L);
    userDao.getEntityManager().flush();
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void deleteWithClass() {
    userDao.delete(100L, ImmutableUserEntity.class);
    userDao.getEntityManager().flush();
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void deleteWithEntity() {
    Optional<ImmutableUserEntity> immutableUserEntity = userDao.get(100L);
    userDao.delete(immutableUserEntity.get());
    userDao.getEntityManager().flush();
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void deleteAll() {
    userDao.deleteAll();
    userDao.getEntityManager().flush();
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void deleteAllWithClass() {
    userDao.deleteAll(ImmutableUserEntity.class);
    userDao.getEntityManager().flush();
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void refresh() {
    Optional<ImmutableUserEntity> user = userDao.get(100L);
    userDao.refresh(user.get());
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void countAll() {
    assertThat(userDao.countAll(), is(3L));
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void countAllWithClass() {
    assertThat(userDao.countAll(ImmutableUserEntity.class), is(3L));
  }

  @Autowired
  public void setUserDao(AbstractPersistentTemporalEntityDao<Long,ImmutableUserEntity> userDao) {
    this.userDao = userDao;
  }
}