package uk.co.jste.daoutils.temporal;

import static uk.co.jste.daoutils.AbstractPersistentTemporalEntityDao.EFFECTIVITY_FILTER;

import java.sql.Timestamp;
import javax.persistence.EntityManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.co.jste.daoutils.AbstractPersistentTemporalEntityDao;

@Aspect
@Component
public class TemporalFilterAdvisor {

  private static final Logger logger = LoggerFactory.getLogger(TemporalFilterAdvisor.class);

  private final EntityManager entityManager;

  public TemporalFilterAdvisor(EntityManager entityManager) {
    this.entityManager = entityManager;
    logger.info("Aspect created");
  }

  @Around(value = "@annotation(DateTimeEffectivityAware)")
  public Object enableOwnerFilter(ProceedingJoinPoint joinPoint) throws Throwable {
    Session session = entityManager.unwrap(Session.class);

    logger.debug("Setting filter {} to value {}", EFFECTIVITY_FILTER, TimeUtils.now());
    Filter filter = session.enableFilter(EFFECTIVITY_FILTER);
    filter.setParameter(AbstractPersistentTemporalEntityDao.EFFECTIVITY_PARAMETER,
        Timestamp.valueOf(TimeUtils.now()));

    Object obj = joinPoint.proceed();
    session.disableFilter(EFFECTIVITY_FILTER);
    return obj;
  }
}
