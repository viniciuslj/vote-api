package viniciuslj.vote.api.domain;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(hidden = true)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = Messages.Agenda.ERROR_SUBJECT_EMPTY)
    private String subject;

    private String description;

    @ApiModelProperty(hidden = true)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Session session;

    @ApiModelProperty(hidden = true)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Result result;

    public void validateSessionExists() {
        if (getSession() == null) {
            throw new EntityNotFoundException(Messages.Session.ERROR_NOT_FOUND);
        }
    }
}
