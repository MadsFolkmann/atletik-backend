package exam.deltager;

import exam.enums.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantRequestDTO {
    private String name;
    private Gender gender;
    private int age;
    private String club;
    private List<Long> disciplineIds;

    @Override
    public String toString() {
        return "ParticipantRequestDTO{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", club='" + club + '\'' +
                ", disciplineIds=" + disciplineIds +
                '}';
    }
}
