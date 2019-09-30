package uk.co.jste.daoutils.types;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
@Embeddable
public class EffectivityTimestampInterval implements EffectivityInterval<LocalDateTime> {

  private static final long serialVersionUID = -5208783949112388001L;

  private static final LocalDateTime maxDateTime = LocalDateTime.of(3456, 2, 1, 0, 0);

  @NotNull
  private LocalDateTime start;

  @NotNull
  private LocalDateTime end = maxDateTime;

  public EffectivityTimestampInterval() {
  }

  public EffectivityTimestampInterval(@NotNull LocalDateTime start, @NotNull LocalDateTime end) {
    this.start = start;
    this.end = end;
  }

  public EffectivityTimestampInterval(EffectivityTimestampInterval other) {
    this.start = other.start;
    this.end = other.end;
  }

  @Override
  public LocalDateTime getStart() {
    return start;
  }

  public void setStart(LocalDateTime start) {
    this.start = start;
  }

  @Override
  public LocalDateTime getEnd() {
    return end;
  }

  public void setEnd(LocalDateTime end) {
    this.end = end;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    EffectivityTimestampInterval effectivityInterval = (EffectivityTimestampInterval) o;
    return Objects.equals(start, effectivityInterval.start) &&
        Objects.equals(end, effectivityInterval.end);
  }

  @Override
  public int hashCode() {
    return Objects.hash(start, end);
  }
}
