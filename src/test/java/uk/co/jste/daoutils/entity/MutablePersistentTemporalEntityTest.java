package uk.co.jste.daoutils.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertFalse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.jste.daoutils.AbstractPersistentTemporalEntityDao;
import uk.co.jste.daoutils.entities.MutableUserEntity;
import uk.co.jste.daoutils.temporal.TimeUtils;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {uk.co.jste.daoutils.config.Configuration.class})
public class MutablePersistentTemporalEntityTest {
  private final LocalDateTime userStartDate = LocalDateTime.of(2000, 1, 1, 12, 0, 0);
  private final LocalDateTime userEndDate = LocalDateTime.of(2020, 1, 1, 12, 0, 0);

  private AbstractPersistentTemporalEntityDao<Long,MutableUserEntity> userDao;

  @Test
  @Sql("/effective-temporal-user.sql")
  public void testGetActiveUsers2000() {
    TimeUtils.setReference(LocalDateTime.of(2000, 1, 1, 12, 0));
    List<MutableUserEntity> allActive = userDao.getAllActive();
    assertThat(allActive, hasSize(1));
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void testGetActiveUsersWithClass2000() {
    TimeUtils.setReference(LocalDateTime.of(2000, 1, 1, 12, 0));
    List<MutableUserEntity> allActive = userDao.getAllActive(MutableUserEntity.class);
    assertThat(allActive, hasSize(1));
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void testGetActiveUsersCount() {
    TimeUtils.setReference(LocalDateTime.of(2000, 1, 1, 12, 0));
    assertThat(userDao.countAllActive(), is(1L));
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void testGetActiveUsersCountWithClass() {
    TimeUtils.setReference(LocalDateTime.of(2000, 1, 1, 12, 0));
    assertThat(userDao.countAllActive(MutableUserEntity.class), is(1L));
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void testGetActiveUserReturnsUserWhenActive() {
    TimeUtils.setReference(LocalDateTime.of(2000, 1, 1, 12, 0));
    Optional<MutableUserEntity> user = userDao.getActive(100L);

    assertThat(user.get().getFirstName(), is("John"));
    assertThat(user.get().getSecondName(), is("Doe"));
    assertThat(user.get().getEffective().getStart(), is(userStartDate));
    assertThat(user.get().getEffective().getEnd(), is(userEndDate));
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void testGetActiveUserReturnsNullWhenNotActive() {
    TimeUtils.setReference(LocalDateTime.of(2000, 1, 1, 12, 0));
    Optional<MutableUserEntity> user = userDao.getActive(101L);
    assertFalse(user.isPresent());
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void testGetActiveUserWithClassReturnsUserWhenActive() {
    TimeUtils.setReference(LocalDateTime.of(2000, 1, 1, 12, 0));
    Optional<MutableUserEntity> user = userDao.getActive(100L, MutableUserEntity.class);

    assertThat(user.get().getFirstName(), is("John"));
    assertThat(user.get().getSecondName(), is("Doe"));
    assertThat(user.get().getEffective().getStart(), is(userStartDate));
    assertThat(user.get().getEffective().getEnd(), is(userEndDate));
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void testGetActiveUserWithClassReturnsNullWhenNotActive() {
    TimeUtils.setReference(LocalDateTime.of(2000, 1, 1, 12, 0));
    Optional<MutableUserEntity> user = userDao.getActive(101L, MutableUserEntity.class);
    assertFalse(user.isPresent());
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void testGetActiveUsers2001() {
    TimeUtils.setReference(LocalDateTime.of(2001, 1, 1, 12, 0));
    List<MutableUserEntity> allActive = userDao.getAllActive();
    assertThat(allActive, hasSize(2));
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void testGetActiveUsersWithClass2001() {
    TimeUtils.setReference(LocalDateTime.of(2001, 1, 1, 12, 0));
    List<MutableUserEntity> allActive = userDao.getAllActive(MutableUserEntity.class);
    assertThat(allActive, hasSize(2));
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void testGetActiveUsers2002() {
    TimeUtils.setReference(LocalDateTime.of(2003, 1, 1, 12, 0));
    List<MutableUserEntity> allActive = userDao.getAllActive();
    assertThat(allActive, hasSize(3));
  }

  @Test
  @Sql("/effective-temporal-user.sql")
  public void testGetActiveUsersWithClass2002() {
    TimeUtils.setReference(LocalDateTime.of(2003, 1, 1, 12, 0));
    List<MutableUserEntity> allActive = userDao.getAllActive(MutableUserEntity.class);
    assertThat(allActive, hasSize(3));
  }

  @Autowired
  public void setUserDao(AbstractPersistentTemporalEntityDao<Long,MutableUserEntity> userDao) {
    this.userDao = userDao;
  }
}