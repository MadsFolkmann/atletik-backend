package exam.disciplin;

import exam.enums.ResultType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineResponseDTO {
    private Long id;
    private String name;
    private ResultType resultType;
}