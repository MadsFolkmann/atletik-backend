package exam.deltager;

import exam.disciplin.DisciplineResponseDTO;
import exam.enums.Gender;
import exam.resultat.ResultResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantResponseDTO {
    private Long id;
    private String name;
    private Gender gender;
    private int age;
    private String club;
    private List<DisciplineResponseDTO> disciplines;
    private List<ResultResponseDTO> results;

    @Override
    public String toString() {
        return "ParticipantResponseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", club='" + club + '\'' +
                ", disciplines=" + disciplines +
                ", results=" + results +
                '}';
    }
}
