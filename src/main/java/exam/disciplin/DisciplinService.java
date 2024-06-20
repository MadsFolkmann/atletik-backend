package exam.disciplin;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DisciplinService {

    private DisciplinRepository disciplinRepository;

    public DisciplinService(DisciplinRepository disciplinRepository) {
        this.disciplinRepository = disciplinRepository;
    }

    public DisciplinResponseDTO createDisciplin(DisciplinRequestDTO disciplinRequestDTO) {
        Disciplin disciplin = new Disciplin();
        disciplin.setNavn(disciplinRequestDTO.getNavn());
        disciplin.setResultatType(disciplinRequestDTO.getResultatType());

        Disciplin savedDisciplin = disciplinRepository.save(disciplin);
        return toDisciplinResponseDTO(savedDisciplin);
    }

    public DisciplinResponseDTO getDisciplinById(Long id) {
        Disciplin disciplin = disciplinRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Disciplin not found"));
        return toDisciplinResponseDTO(disciplin);
    }

    public DisciplinResponseDTO updateDisciplin(Long id, DisciplinRequestDTO disciplinRequestDTO) {
        Disciplin disciplin = disciplinRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Disciplin not found"));
        disciplin.setNavn(disciplinRequestDTO.getNavn());
        disciplin.setResultatType(disciplinRequestDTO.getResultatType());

        Disciplin updatedDisciplin = disciplinRepository.save(disciplin);
        return toDisciplinResponseDTO(updatedDisciplin);
    }

    public void deleteDisciplin(Long id) {
        Disciplin disciplin = disciplinRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Disciplin not found"));
        disciplinRepository.delete(disciplin);
    }

    public List<DisciplinResponseDTO> getAllDiscipliner() {
        List<Disciplin> discipliner = disciplinRepository.findAll();
        return discipliner.stream().map(this::toDisciplinResponseDTO).collect(Collectors.toList());
    }

    private DisciplinResponseDTO toDisciplinResponseDTO(Disciplin disciplin) {
        return new DisciplinResponseDTO(
                disciplin.getId(),
                disciplin.getNavn(),
                disciplin.getResultatType()
        );
    }
}

