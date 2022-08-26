package viniciuslj.vote.api.domain;

import lombok.Data;
import viniciuslj.vote.api.Messages;
import viniciuslj.vote.api.services.exceptions.EntityNotFoundException;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = Messages.Agenda.ERROR_SUBJECT_EMPTY)
    private String subject;

    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Session session;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Result result;

    public void validateSessionExists() {
        if (getSession() == null) {
            throw new EntityNotFoundException(Messages.Session.ERROR_NOT_FOUND);
        }
    }
}
