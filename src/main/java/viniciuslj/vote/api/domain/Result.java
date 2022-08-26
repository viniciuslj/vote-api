package viniciuslj.vote.api.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import java.util.Map;

@Data
@Entity
public class Result {
    @Id
    @PrimaryKeyJoinColumn
    @ApiModelProperty(hidden = true)
    private Long agendaId;

    @Column(nullable = false)
    private boolean approved;

    @Column(nullable = false)
    private Long totalYes;

    @Column(nullable = false)
    private Long totalNo;

    public void setTotals(Map<String, Long> totals) {
        Long totalYes = totals.get("totalYes");
        Long totalNo = totals.get("totalNo");

        setTotalYes(totalYes != null ? totalYes : 0L);
        setTotalNo(totalNo != null ? totalNo : 0L);
    }
}
