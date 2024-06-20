package exam.deltager;

import exam.disciplin.Discipline;
import exam.enums.Gender;
import exam.resultat.Result;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int age;
    private String club;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "participant_discipline", joinColumns = @JoinColumn(name = "participant_id"), inverseJoinColumns = @JoinColumn(name = "discipline_id"))
    private List<Discipline> disciplines;

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Result> results;
}
