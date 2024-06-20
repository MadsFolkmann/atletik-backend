package exam.deltager;
import exam.disciplin.Disciplin;

import exam.enums.Gender;
import exam.resultat.Resultat;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Deltager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String navn;
    @Enumerated(EnumType.STRING)
    private Gender køn;
    private int alder;
    private String klub;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "deltager_disciplin", joinColumns = @JoinColumn(name = "deltager_id"), inverseJoinColumns = @JoinColumn(name = "disciplin_id"))
    private List<Disciplin> discipliner;

    @OneToMany(mappedBy = "deltager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resultat> resultater;
}

