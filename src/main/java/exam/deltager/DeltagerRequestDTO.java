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
public class DeltagerRequestDTO {
    private String navn;
    private Gender køn;
    private int alder;
    private String klub;
    private List<Long> disciplinIds;
}
