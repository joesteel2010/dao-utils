package uk.co.jste.daoutils.config;

import javax.persistence.EntityManager;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import uk.co.jste.daoutils.AbstractPersistentTemporalEntityDao;
import uk.co.jste.daoutils.entities.ImmutableUserEntity;
import uk.co.jste.daoutils.entities.MutableUserEntity;

@EntityScan("uk.co.jste")
@ComponentScan("uk.co.jste")
@EnableJpaRepositories("uk.co.jste")
@EnableAutoConfiguration
@EnableTransactionManagement
@TestPropertySource("/application.properties")
@org.springframework.context.annotation.Configuration
public class Configuration {
  @Bean
  public AbstractPersistentTemporalEntityDao<Long,ImmutableUserEntity> getImmutableUserEntityDao(EntityManager entityManager) {
    return new AbstractPersistentTemporalEntityDao<Long,ImmutableUserEntity>(entityManager) {
      @Override
      public Class<ImmutableUserEntity> getEntityClass() {
        return ImmutableUserEntity.class;
      }
    };
  }

  @Bean
  public AbstractPersistentTemporalEntityDao<Long,MutableUserEntity> getMutableUserEntityDao(EntityManager entityManager) {
    return new AbstractPersistentTemporalEntityDao<Long,MutableUserEntity>(entityManager) {
      @Override
      public Class<MutableUserEntity> getEntityClass() {
        return MutableUserEntity.class;
      }
    };
  }
}
