package exam.resultat;

import exam.deltager.Participant;
import exam.disciplin.Discipline;
import exam.enums.ResultType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Enumerated(EnumType.STRING)
    private ResultType resultType;

    private double resultValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Participant participant;
}