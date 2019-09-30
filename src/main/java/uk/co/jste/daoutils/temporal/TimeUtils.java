package uk.co.jste.daoutils.temporal;

import java.time.LocalDateTime;

/**
 * Static utilities dealing with <i>time</i>.
 *
 * <p>This class controls the record time using a {@link ReferenceTimeProvider}. You can override
 * the default {@link ThreadLocalReferenceProvider} implementation by calling the {@link
 * #setReferenceDateTimeProvider(ReferenceTimeProvider) setReferenceDateTimeProvider} method upon
 * application startup.
 *
 * @see ReferenceTimeProvider
 */
public final class TimeUtils {

  // time framing functionality
  private static ReferenceTimeProvider<LocalDateTime> referenceDateTimeProvider = new ThreadLocalReferenceProvider<>();

  private TimeUtils() throws IllegalAccessException {
    // no need to instantiate this class
    throw new IllegalAccessException("This class should not be instantiated");
  }

  /**
   * Overrides the default {@link ReferenceTimeProvider} implementation.
   */
  @SuppressWarnings({"WeakerAccess", "unused"})
  public static synchronized void setReferenceDateTimeProvider(
      ReferenceTimeProvider<LocalDateTime> provider) {
    TimeUtils.referenceDateTimeProvider = provider;
  }

  /**
   * Determines whether or not a reference time has been set.
   */
  @SuppressWarnings("WeakerAccess")
  public static boolean isReferenceSet() {
    return referenceDateTimeProvider.getReference() != null;
  }

  /**
   * Returns the reference time, or <i>wallclock now</i> if no reference time has been set.
   */
  @SuppressWarnings("WeakerAccess")
  public static LocalDateTime reference() {
    return isReferenceSet() ? referenceDateTimeProvider.getReference() : current();
  }

  /**
   * Set the reference time to the specified value.
   */
  public static void setReference(LocalDateTime dateTime) {
    referenceDateTimeProvider.setReference(dateTime);
  }

  /**
   * Clears the reference time.
   */
  @SuppressWarnings("unused")
  public static void clearReference() {
    referenceDateTimeProvider.clearReference();
  }

  /**
   * Create a {@link LocalDateTime} object representing given day of given month in given year.
   */
  @SuppressWarnings("unused")
  public static LocalDateTime day(int day, int month, int year) {
    return LocalDateTime.of(year, month, day, 0, 0, 0, 0);
  }

  /**
   * Returns the current reference time. If a reference time is set, it is that reference time that
   * will be returned.
   *
   * @see #reference()
   */
  public static LocalDateTime now() {
    return reference();
  }

  /**
   * Returns the current local time (<i>wallclock now</i>), ignoring the reference time.
   */
  @SuppressWarnings("WeakerAccess")
  public static LocalDateTime current() {
    return LocalDateTime.now();
  }
}
