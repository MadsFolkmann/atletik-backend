package exam.resultat;
import exam.deltager.Deltager;
import exam.deltager.DeltagerRepository;
import exam.deltager.DeltagerResponseDTO;
import exam.disciplin.Disciplin;
import exam.disciplin.DisciplinRepository;
import exam.disciplin.DisciplinResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;



@Service
@Transactional
public class ResultatService {

    private ResultatRepository resultatRepository;
    private DeltagerRepository deltagerRepository;
    private DisciplinRepository disciplinRepository;

    public ResultatService(ResultatRepository resultatRepository, DeltagerRepository deltagerRepository, DisciplinRepository disciplinRepository) {
        this.resultatRepository = resultatRepository;
        this.deltagerRepository = deltagerRepository;
        this.disciplinRepository = disciplinRepository;
    }

    public ResultatResponseDTO createResultat(ResultatRequestDTO resultatRequestDTO) {
        Deltager deltager = deltagerRepository.findById(resultatRequestDTO.getDeltagerId())
                .orElseThrow(() -> new EntityNotFoundException("Deltager not found"));
        Disciplin disciplin = disciplinRepository.findById(resultatRequestDTO.getDisciplinId())
                .orElseThrow(() -> new EntityNotFoundException("Disciplin not found"));

        Resultat resultat = new Resultat();
        resultat.setDato(resultatRequestDTO.getDato());
        resultat.setResultatType(resultatRequestDTO.getResultatType());
        resultat.setResultatVærdi(resultatRequestDTO.getResultatVærdi());
        resultat.setDeltager(deltager);
        resultat.setDisciplin(disciplin);

        Resultat savedResultat = resultatRepository.save(resultat);
        return toResultatResponseDTO(savedResultat);
    }

    public ResultatResponseDTO getResultatById(Long id) {
        Resultat resultat = resultatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Resultat not found"));
        return toResultatResponseDTO(resultat);
    }

    public ResultatResponseDTO updateResultat(Long id, ResultatRequestDTO resultatRequestDTO) {
        Resultat resultat = resultatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Resultat not found"));
        Deltager deltager = deltagerRepository.findById(resultatRequestDTO.getDeltagerId())
                .orElseThrow(() -> new EntityNotFoundException("Deltager not found"));
        Disciplin disciplin = disciplinRepository.findById(resultatRequestDTO.getDisciplinId())
                .orElseThrow(() -> new EntityNotFoundException("Disciplin not found"));

        resultat.setDato(resultatRequestDTO.getDato());
        resultat.setResultatType(resultatRequestDTO.getResultatType());
        resultat.setResultatVærdi(resultatRequestDTO.getResultatVærdi());
        resultat.setDeltager(deltager);
        resultat.setDisciplin(disciplin);

        Resultat updatedResultat = resultatRepository.save(resultat);
        return toResultatResponseDTO(updatedResultat);
    }

    public void deleteResultat(Long id) {
        Resultat resultat = resultatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Resultat not found"));
        resultatRepository.delete(resultat);
    }

    public List<ResultatResponseDTO> getAllResultater() {
        List<Resultat> resultater = resultatRepository.findAll();
        return resultater.stream().map(this::toResultatResponseDTO).collect(Collectors.toList());
    }

    private ResultatResponseDTO toResultatResponseDTO(Resultat resultat) {
        return new ResultatResponseDTO(
                resultat.getId(),
                resultat.getDato(),
                resultat.getResultatType(),
                resultat.getResultatVærdi(),
                new DisciplinResponseDTO(resultat.getDisciplin().getId(), resultat.getDisciplin().getNavn(), resultat.getDisciplin().getResultatType()),
                new DeltagerResponseDTO(resultat.getDeltager().getId(), resultat.getDeltager().getNavn(), resultat.getDeltager().getKøn(), resultat.getDeltager().getAlder(), resultat.getDeltager().getKlub(), null, null)
        );
    }
}
