package uk.co.jste.daoutils.temporal;

/**
 * Strategy for dealing with the reference time.
 */
public interface ReferenceTimeProvider<T> {

  /**
   * Returns the reference time, possibly <tt>null</tt>.
   */
  T getReference();

  /**
   * Sets the reference time to the specified value.
   */
  void setReference(T dateTime);

  /**
   * Clears the reference time. After clearing, {@link #getReference() getReference} should return
   * <tt>null</tt> to indicate the reference time is no longer being set.
   */
  void clearReference();
}
