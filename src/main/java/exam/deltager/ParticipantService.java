package exam.deltager;


import exam.disciplin.Discipline;
import exam.disciplin.DisciplineRepository;
import exam.disciplin.DisciplineResponseDTO;
import exam.resultat.Result;
import exam.resultat.ResultRepository;
import exam.resultat.ResultResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final DisciplineRepository disciplineRepository;
    private final ResultRepository resultRepository;

    public ParticipantService(ParticipantRepository participantRepository, DisciplineRepository disciplineRepository, ResultRepository resultRepository) {
        this.participantRepository = participantRepository;
        this.disciplineRepository = disciplineRepository;
        this.resultRepository = resultRepository;
    }

    public ParticipantResponseDTO createParticipant(ParticipantRequestDTO participantRequestDTO) {
        Participant participant = new Participant();
        participant.setName(participantRequestDTO.getName());
        participant.setGender(participantRequestDTO.getGender());
        participant.setAge(participantRequestDTO.getAge());
        participant.setClub(participantRequestDTO.getClub());

        List<Discipline> disciplines = disciplineRepository.findAllById(participantRequestDTO.getDisciplineIds());
        participant.setDisciplines(disciplines);

        Participant savedParticipant = participantRepository.save(participant);
        return toParticipantResponseDTO(savedParticipant);
    }

    public ParticipantResponseDTO getParticipantById(Long id) {
        Participant participant = participantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Participant not found"));
        return toParticipantResponseDTO(participant);
    }

    public ParticipantResponseDTO updateParticipant(Long id, ParticipantRequestDTO participantRequestDTO) {
        Participant participant = participantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Participant not found"));
        participant.setName(participantRequestDTO.getName());
        participant.setGender(participantRequestDTO.getGender());
        participant.setAge(participantRequestDTO.getAge());
        participant.setClub(participantRequestDTO.getClub());

        List<Discipline> disciplines = disciplineRepository.findAllById(participantRequestDTO.getDisciplineIds());
        participant.setDisciplines(disciplines);

        Participant updatedParticipant = participantRepository.save(participant);
        return toParticipantResponseDTO(updatedParticipant);
    }

    public void deleteParticipant(Long id) {
        Participant participant = participantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Participant not found"));
        participantRepository.delete(participant);
    }

    public List<ParticipantResponseDTO> getAllParticipants() {
        List<Participant> participants = participantRepository.findAll();
        return participants.stream().map(this::toParticipantResponseDTO).collect(Collectors.toList());
    }

    private ParticipantResponseDTO toParticipantResponseDTO(Participant participant) {
        return new ParticipantResponseDTO(
                participant.getId(),
                participant.getName(),
                participant.getGender(),
                participant.getAge(),
                participant.getClub(),
                participant.getDisciplines().stream().map(this::toDisciplineResponseDTO).collect(Collectors.toList()),
                participant.getResults().stream().map(this::toResultResponseDTO).collect(Collectors.toList())
        );
    }

    private DisciplineResponseDTO toDisciplineResponseDTO(Discipline discipline) {
        return new DisciplineResponseDTO(
                discipline.getId(),
                discipline.getName(),
                discipline.getResultType()
        );
    }

    private ResultResponseDTO toResultResponseDTO(Result result) {
        return new ResultResponseDTO(
                result.getId(),
                result.getDate(),
                result.getResultType(),
                result.getResultValue(),
                toDisciplineResponseDTO(result.getDiscipline()),
                new ParticipantResponseDTO(
                        result.getParticipant().getId(),
                        result.getParticipant().getName(),
                        result.getParticipant().getGender(),
                        result.getParticipant().getAge(),
                        result.getParticipant().getClub(),
                        null,
                        null
                )
        );
    }
}