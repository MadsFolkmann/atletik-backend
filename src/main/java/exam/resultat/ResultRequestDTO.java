package exam.resultat;

import exam.enums.ResultType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultRequestDTO {
    private Date date;
    private ResultType resultType;
    private double resultValue;
    private Long disciplineId;
    private Long participantId;
}
