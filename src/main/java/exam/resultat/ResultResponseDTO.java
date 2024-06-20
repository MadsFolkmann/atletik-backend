package exam.resultat;

import exam.deltager.ParticipantResponseDTO;
import exam.disciplin.DisciplineResponseDTO;
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
public class ResultResponseDTO {
    private Long id;
    private Date date;
    private ResultType resultType;
    private double resultValue;
    private DisciplineResponseDTO discipline;
    private ParticipantResponseDTO participant;
}

