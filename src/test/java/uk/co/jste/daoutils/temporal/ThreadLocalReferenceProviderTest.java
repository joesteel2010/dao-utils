package uk.co.jste.daoutils.temporal;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

public class ThreadLocalReferenceProviderTest {
  private ThreadLocalReferenceProvider threadLocalReferenceProvider;

  @Before
  public void setUp() {
    threadLocalReferenceProvider = new ThreadLocalReferenceProvider();
  }

  @Test
  public void getReference() {
    LocalDateTime ref = LocalDateTime.of(2000,1,1,0,0);
    threadLocalReferenceProvider.setReference(ref);
    assertTrue(threadLocalReferenceProvider.getReference() == ref);
  }

  @Test
  public void clearReference() {
    LocalDateTime ref = LocalDateTime.of(2000,1,1,0,0);
    threadLocalReferenceProvider.setReference(ref);
    threadLocalReferenceProvider.clearReference();
    assertThat(threadLocalReferenceProvider.getReference(), is(nullValue()));
  }
}