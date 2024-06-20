package exam.deltager;


import exam.disciplin.Disciplin;
import exam.disciplin.DisciplinRepository;
import exam.disciplin.DisciplinResponseDTO;
import exam.resultat.Resultat;
import exam.resultat.ResultatRepository;
import exam.resultat.ResultatResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeltagerService {

    private final DeltagerRepository deltagerRepository;
    private final DisciplinRepository disciplinRepository;
    private final ResultatRepository resultatRepository;

    public DeltagerService(DeltagerRepository deltagerRepository, DisciplinRepository disciplinRepository, ResultatRepository resultatRepository) {
        this.deltagerRepository = deltagerRepository;
        this.disciplinRepository = disciplinRepository;
        this.resultatRepository = resultatRepository;
    }

    public DeltagerResponseDTO createDeltager(DeltagerRequestDTO deltagerRequestDTO) {
        Deltager deltager = new Deltager();
        deltager.setNavn(deltagerRequestDTO.getNavn());
        deltager.setKøn(deltagerRequestDTO.getKøn());
        deltager.setAlder(deltagerRequestDTO.getAlder());
        deltager.setKlub(deltagerRequestDTO.getKlub());

        List<Disciplin> discipliner = disciplinRepository.findAllById(deltagerRequestDTO.getDisciplinIds());
        deltager.setDiscipliner(discipliner);

        Deltager savedDeltager = deltagerRepository.save(deltager);
        return toDeltagerResponseDTO(savedDeltager);
    }

    public DeltagerResponseDTO getDeltagerById(Long id) {
        Deltager deltager = deltagerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Deltager not found"));
        return toDeltagerResponseDTO(deltager);
    }

    public DeltagerResponseDTO updateDeltager(Long id, DeltagerRequestDTO deltagerRequestDTO) {
        Deltager deltager = deltagerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Deltager not found"));
        deltager.setNavn(deltagerRequestDTO.getNavn());
        deltager.setKøn(deltagerRequestDTO.getKøn());
        deltager.setAlder(deltagerRequestDTO.getAlder());
        deltager.setKlub(deltagerRequestDTO.getKlub());

        List<Disciplin> discipliner = disciplinRepository.findAllById(deltagerRequestDTO.getDisciplinIds());
        deltager.setDiscipliner(discipliner);

        Deltager updatedDeltager = deltagerRepository.save(deltager);
        return toDeltagerResponseDTO(updatedDeltager);
    }

    public void deleteDeltager(Long id) {
        Deltager deltager = deltagerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Deltager not found"));
        deltagerRepository.delete(deltager);
    }

    public List<DeltagerResponseDTO> getAllDeltagere() {
        List<Deltager> deltagere = deltagerRepository.findAll();
        return deltagere.stream().map(this::toDeltagerResponseDTO).collect(Collectors.toList());
    }

    private DeltagerResponseDTO toDeltagerResponseDTO(Deltager deltager) {
        return new DeltagerResponseDTO(
                deltager.getId(),
                deltager.getNavn(),
                deltager.getKøn(),
                deltager.getAlder(),
                deltager.getKlub(),
                deltager.getDiscipliner().stream().map(this::toDisciplinResponseDTO).collect(Collectors.toList()),
                deltager.getResultater().stream().map(this::toResultatResponseDTO).collect(Collectors.toList())
        );
    }

    private DisciplinResponseDTO toDisciplinResponseDTO(Disciplin disciplin) {
        return new DisciplinResponseDTO(
                disciplin.getId(),
                disciplin.getNavn(),
                disciplin.getResultatType()
        );
    }

    private ResultatResponseDTO toResultatResponseDTO(Resultat resultat) {
        return new ResultatResponseDTO(
                resultat.getId(),
                resultat.getDato(),
                resultat.getResultatType(),
                resultat.getResultatVærdi(),
                toDisciplinResponseDTO(resultat.getDisciplin()),
                new DeltagerResponseDTO(
                        resultat.getDeltager().getId(),
                        resultat.getDeltager().getNavn(),
                        resultat.getDeltager().getKøn(),
                        resultat.getDeltager().getAlder(),
                        resultat.getDeltager().getKlub(),
                        null,
                        null
                )
        );
    }
}