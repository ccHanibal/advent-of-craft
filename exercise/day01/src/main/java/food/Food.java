package food;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Supplier;

public record Food(
    LocalDate expirationDate,
    Boolean approvedForConsumption,
    UUID inspectorId) {

    public boolean isEdible(Supplier<LocalDate> now) {
        if (isExpired(now))
            return false;

        if (!this.approvedForConsumption)
            return false;

        if (this.inspectorId == null)
            return false;

        return true;
    }

    private boolean isExpired(Supplier<LocalDate> now) {
        return !this.expirationDate.isAfter(now.get());
    }
}
