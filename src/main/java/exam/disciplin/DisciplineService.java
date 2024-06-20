package exam.disciplin;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
public class DisciplineService {

    private final DisciplineRepository disciplineRepository;

    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public DisciplineResponseDTO createDiscipline(DisciplineRequestDTO disciplineRequestDTO) {
        Discipline discipline = new Discipline();
        discipline.setName(disciplineRequestDTO.getName());
        discipline.setResultType(disciplineRequestDTO.getResultType());

        Discipline savedDiscipline = disciplineRepository.save(discipline);
        return toDisciplineResponseDTO(savedDiscipline);
    }

    public DisciplineResponseDTO getDisciplineById(Long id) {
        Discipline discipline = disciplineRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Discipline not found"));
        return toDisciplineResponseDTO(discipline);
    }

    public DisciplineResponseDTO updateDiscipline(Long id, DisciplineRequestDTO disciplineRequestDTO) {
        Discipline discipline = disciplineRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Discipline not found"));
        discipline.setName(disciplineRequestDTO.getName());
        discipline.setResultType(disciplineRequestDTO.getResultType());

        Discipline updatedDiscipline = disciplineRepository.save(discipline);
        return toDisciplineResponseDTO(updatedDiscipline);
    }

    public void deleteDiscipline(Long id) {
        Discipline discipline = disciplineRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Discipline not found"));
        disciplineRepository.delete(discipline);
    }

    public List<DisciplineResponseDTO> getAllDisciplines() {
        List<Discipline> disciplines = disciplineRepository.findAll();
        return disciplines.stream().map(this::toDisciplineResponseDTO).collect(Collectors.toList());
    }

    private DisciplineResponseDTO toDisciplineResponseDTO(Discipline discipline) {
        return new DisciplineResponseDTO(
                discipline.getId(),
                discipline.getName(),
                discipline.getResultType()
        );
    }
}
