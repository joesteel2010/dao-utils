package uk.co.jste.daoutils.types;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import org.junit.Test;

public class EffectivityTimestampIntervalTest {

  private final LocalDateTime start = LocalDateTime.of(2000, 1, 1, 0, 0);
  private final LocalDateTime end = LocalDateTime.of(2001, 1, 1, 0, 0);
  private EffectivityTimestampInterval effectivityTimestampInterval;

  @Test
  public void testCopyConstructor() {
    effectivityTimestampInterval = new EffectivityTimestampInterval();
    effectivityTimestampInterval.setStart(start);
    effectivityTimestampInterval.setStart(end);

    EffectivityTimestampInterval newEffectivityTimestampInterval = new EffectivityTimestampInterval(
        new EffectivityTimestampInterval(effectivityTimestampInterval));

    assertThat(effectivityTimestampInterval, is(newEffectivityTimestampInterval));
  }

  @Test
  public void testEquals() {
    EffectivityTimestampInterval effectivityTimestampIntervalA = new EffectivityTimestampInterval();
    effectivityTimestampIntervalA.setStart(start);
    effectivityTimestampIntervalA.setEnd(end);

    EffectivityTimestampInterval effectivityTimestampIntervalB = new EffectivityTimestampInterval();
    effectivityTimestampIntervalB.setStart(start);
    effectivityTimestampIntervalB.setEnd(end);

    assertTrue(effectivityTimestampIntervalA.equals(effectivityTimestampIntervalB));
  }

  @Test
  public void testEqualsTrueForSameInstance() {
    EffectivityTimestampInterval effectivityTimestampIntervalA = new EffectivityTimestampInterval();
    effectivityTimestampIntervalA.setStart(start);
    effectivityTimestampIntervalA.setEnd(end);


    assertTrue(effectivityTimestampIntervalA.equals(effectivityTimestampIntervalA));
  }

  @Test
  public void testEqualsFalseForDifferentObjects() {
    EffectivityTimestampInterval effectivityTimestampIntervalA = new EffectivityTimestampInterval();
    effectivityTimestampIntervalA.setStart(start);
    effectivityTimestampIntervalA.setEnd(end);

    assertFalse(effectivityTimestampIntervalA.equals(new Object()));
  }

  @Test
  public void testHashcode() {
    EffectivityTimestampInterval effectivityTimestampIntervalA = new EffectivityTimestampInterval();
    effectivityTimestampIntervalA.setStart(start);
    effectivityTimestampIntervalA.setEnd(end);

    EffectivityTimestampInterval effectivityTimestampIntervalB = new EffectivityTimestampInterval();
    effectivityTimestampIntervalB.setStart(start);
    effectivityTimestampIntervalB.setEnd(end);

    assertThat(effectivityTimestampIntervalA.hashCode(), is(effectivityTimestampIntervalB.hashCode()));
  }
}