package exam.disciplin;

import exam.enums.ResultatType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinRequestDTO {
    private String navn;
    private ResultatType resultatType;
}
