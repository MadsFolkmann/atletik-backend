package exam.resultat;
import exam.deltager.Participant;
import exam.deltager.ParticipantRepository;
import exam.deltager.ParticipantResponseDTO;
import exam.disciplin.Discipline;
import exam.disciplin.DisciplineRepository;
import exam.disciplin.DisciplineResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;



@Service
@Transactional
public class ResultService {

    private final ResultRepository resultRepository;
    private final ParticipantRepository participantRepository;
    private final DisciplineRepository disciplineRepository;

    public ResultService(ResultRepository resultRepository, ParticipantRepository participantRepository, DisciplineRepository disciplineRepository) {
        this.resultRepository = resultRepository;
        this.participantRepository = participantRepository;
        this.disciplineRepository = disciplineRepository;
    }

    public ResultResponseDTO createResult(ResultRequestDTO resultRequestDTO) {
        Participant participant = participantRepository.findById(resultRequestDTO.getParticipantId())
                .orElseThrow(() -> new EntityNotFoundException("Participant not found"));
        Discipline discipline = disciplineRepository.findById(resultRequestDTO.getDisciplineId())
                .orElseThrow(() -> new EntityNotFoundException("Discipline not found"));

        Result result = new Result();
        result.setDate(resultRequestDTO.getDate());
        result.setResultType(resultRequestDTO.getResultType());
        result.setResultValue(resultRequestDTO.getResultValue());
        result.setParticipant(participant);
        result.setDiscipline(discipline);

        Result savedResult = resultRepository.save(result);
        return toResultResponseDTO(savedResult);
    }

    public ResultResponseDTO getResultById(Long id) {
        Result result = resultRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Result not found"));
        return toResultResponseDTO(result);
    }

    public ResultResponseDTO updateResult(Long id, ResultRequestDTO resultRequestDTO) {
        Result result = resultRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Result not found"));
        Participant participant = participantRepository.findById(resultRequestDTO.getParticipantId())
                .orElseThrow(() -> new EntityNotFoundException("Participant not found"));
        Discipline discipline = disciplineRepository.findById(resultRequestDTO.getDisciplineId())
                .orElseThrow(() -> new EntityNotFoundException("Discipline not found"));

        result.setDate(resultRequestDTO.getDate());
        result.setResultType(resultRequestDTO.getResultType());
        result.setResultValue(resultRequestDTO.getResultValue());
        result.setParticipant(participant);
        result.setDiscipline(discipline);

        Result updatedResult = resultRepository.save(result);
        return toResultResponseDTO(updatedResult);
    }

    public void deleteResult(Long id) {
        Result result = resultRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Result not found"));
        resultRepository.delete(result);
    }

    public List<ResultResponseDTO> getAllResults() {
        List<Result> results = resultRepository.findAll();
        return results.stream().map(this::toResultResponseDTO).collect(Collectors.toList());
    }

    private ResultResponseDTO toResultResponseDTO(Result result) {
        return new ResultResponseDTO(
                result.getId(),
                result.getDate(),
                result.getResultType(),
                result.getResultValue(),
                new DisciplineResponseDTO(result.getDiscipline().getId(), result.getDiscipline().getName(), result.getDiscipline().getResultType()),
                new ParticipantResponseDTO(result.getParticipant().getId(), result.getParticipant().getName(), result.getParticipant().getGender(), result.getParticipant().getAge(), result.getParticipant().getClub(), null, null)
        );
    }
}
