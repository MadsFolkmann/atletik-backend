package exam.disciplin;

import exam.deltager.Participant;
import exam.enums.ResultType;
import exam.resultat.Result;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private ResultType resultType;

    @ManyToMany(mappedBy = "disciplines", fetch = FetchType.LAZY)
    private List<Participant> participants;

    @OneToMany(mappedBy = "discipline", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Result> results;
}
