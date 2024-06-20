package exam.resultat;

import exam.deltager.Deltager;
import exam.disciplin.Disciplin;
import exam.enums.ResultatType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Resultat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dato;

    @Enumerated(EnumType.STRING)
    private ResultatType resultatType;

    private double resultatVærdi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplin_id")
    private Disciplin disciplin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deltager_id")
    private Deltager deltager;
}