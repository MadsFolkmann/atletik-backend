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
public class DisciplineRequestDTO {
    private String name;
    private ResultType resultType;
}