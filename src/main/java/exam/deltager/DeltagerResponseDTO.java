package exam.deltager;

import exam.disciplin.DisciplinResponseDTO;
import exam.enums.Gender;
import exam.resultat.ResultatResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeltagerResponseDTO {
    private Long id;
    private String navn;
    private Gender køn;
    private int alder;
    private String klub;
    private List<DisciplinResponseDTO> discipliner;
    private List<ResultatResponseDTO> resultater;
}
