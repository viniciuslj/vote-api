package viniciuslj.vote.api.domain;

import lombok.Data;
import viniciuslj.vote.api.Messages;
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
    @NotNull(message = Messages.Session.ERROR_START_REQUIRED)
    private LocalDateTime startsAt;

    private LocalDateTime endsAt;

    public void setDefaultEndsAtIfNull() {
        if (getEndsAt() == null){
            setEndsAt(getStartsAt().plusMinutes(1));
        }
    }

    public void validatePeriod() {
        if (!getStartsAt().isAfter(LocalDateTime.now())) {
            throw new BusinessLogicException(Messages.Session.ERROR_START_NOT_FUTURE);
        }

        if (!getEndsAt().isAfter(getStartsAt())) {
            throw new BusinessLogicException(Messages.Session.ERROR_END_NOT_AFTER_START);
        }
    }

    public void validateIsOpen() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(getStartsAt()) || now.isAfter(getEndsAt())) {
            throw new BusinessLogicException(Messages.Session.ERROR_NOT_OPEN);
        }
    }
}
