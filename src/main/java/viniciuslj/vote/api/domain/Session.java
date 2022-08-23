package viniciuslj.vote.api.domain;

import lombok.Data;
import viniciuslj.vote.api.services.exceptions.BusinessLogicException;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
public class Session {
    @Id
    @PrimaryKeyJoinColumn
    private Long agendaId;

    @Column(nullable = false)
    @NotNull(message = "Start date and time is required")
    private LocalDateTime startsAt;

    private LocalDateTime endsAt;

    public void setDefaultEndsAtIfNull() {
        if (getEndsAt() == null){
            setEndsAt(getStartsAt().plusMinutes(1));
        }
    }

    public void validatePeriod() {
        if (!getStartsAt().isAfter(LocalDateTime.now())) {
            throw new BusinessLogicException("Start date and time must be in the future");
        }

        if (!getEndsAt().isAfter(getStartsAt())) {
            throw new BusinessLogicException("End date and time must be after the start");
        }
    }

    public void validateIsOpen() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(getStartsAt()) || now.isAfter(getEndsAt())) {
            throw new BusinessLogicException("The session is not open");
        }
    }
}
