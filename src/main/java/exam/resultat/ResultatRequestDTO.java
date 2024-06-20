package exam.resultat;

import exam.enums.ResultatType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultatRequestDTO {
    private Date dato;
    private ResultatType resultatType;
    private double resultatVærdi;
    private Long disciplinId;
    private Long deltagerId;
}

