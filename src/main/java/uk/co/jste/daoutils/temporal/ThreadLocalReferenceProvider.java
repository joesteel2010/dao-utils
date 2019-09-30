package uk.co.jste.daoutils.temporal;

/**
 * {@link ReferenceTimeProvider} which holds the reference time in a thread-local variable.
 *
 * <p>If you need to deal with the reference time in a multi-threaded way, you should use a custom
 * {@link ReferenceTimeProvider} implementation.
 *
 * @see ReferenceTimeProvider
 */
public class ThreadLocalReferenceProvider<T> implements ReferenceTimeProvider<T> {

  private static final ThreadLocal reference = new ThreadLocal();

  /**
   * @see ReferenceTimeProvider#getReference()
   */
  @Override
  @SuppressWarnings("unchecked")
  public T getReference() {
    return (T) reference.get();
  }

  /**
   * @see ReferenceTimeProvider#setReference(T)
   */
  @Override
  @SuppressWarnings("unchecked")
  public void setReference(T dateTime) {
    reference.set(dateTime);
  }

  /**
   * @see ReferenceTimeProvider#clearReference()
   */
  @Override
  public void clearReference() {
    reference.remove();
  }
}
