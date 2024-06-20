package exam.disciplin;

import exam.deltager.Deltager;
import exam.enums.ResultatType;
import exam.resultat.Resultat;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Disciplin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String navn;

    @Enumerated(EnumType.STRING)
    private ResultatType resultatType;

    @ManyToMany(mappedBy = "discipliner", fetch = FetchType.LAZY)
    private List<Deltager> deltagere;

    @OneToMany(mappedBy = "disciplin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resultat> resultater;
}

