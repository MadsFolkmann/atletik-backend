package exam.resultat;

import exam.deltager.DeltagerResponseDTO;
import exam.disciplin.DisciplinResponseDTO;
import exam.enums.ResultatType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultatResponseDTO {
    private Long id;
    private Date dato;
    private ResultatType resultatType;
    private double resultatVærdi;
    private DisciplinResponseDTO disciplin;
    private DeltagerResponseDTO deltager;
}

