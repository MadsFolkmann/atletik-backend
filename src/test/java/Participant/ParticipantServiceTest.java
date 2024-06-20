package Participant;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import exam.deltager.Participant;
import exam.deltager.ParticipantRepository;
import exam.deltager.ParticipantRequestDTO;
import exam.deltager.ParticipantResponseDTO;
import exam.deltager.ParticipantService;
import exam.disciplin.Discipline;
import exam.disciplin.DisciplineRepository;
import exam.enums.Gender;
import exam.enums.ResultType;
import exam.resultat.ResultRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ParticipantServiceTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private DisciplineRepository disciplineRepository;

    @InjectMocks
    private ParticipantService participantService;

    private Participant participant;
    private ParticipantRequestDTO participantRequest;
    private Discipline discipline;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        discipline = new Discipline(1L, "100m Sprint", ResultType.TIME, null, null);  // Tilføj null for resultater og deltagere
        participant = new Participant(1L, "John Doe", Gender.MALE, 25, "Some Club", Collections.singletonList(discipline), null);
        participantRequest = new ParticipantRequestDTO("John Doe", Gender.MALE, 25, "Some Club", Collections.singletonList(1L));
    }

    @Test
    public void testCreateParticipant() {
        when(disciplineRepository.findAllById(participantRequest.getDisciplineIds())).thenReturn(Collections.singletonList(discipline));
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);

        ParticipantResponseDTO response = participantService.createParticipant(participantRequest);

        assertNotNull(response);
        assertEquals(participant.getName(), response.getName());
        assertEquals(participant.getGender(), response.getGender());
        assertEquals(participant.getAge(), response.getAge());
        assertEquals(participant.getClub(), response.getClub());
    }

    @Test
    public void testGetParticipantById() {
        when(participantRepository.findById(1L)).thenReturn(Optional.of(participant));

        ParticipantResponseDTO response = participantService.getParticipantById(1L);

        assertNotNull(response);
        assertEquals(participant.getName(), response.getName());
    }

    @Test
    public void testUpdateParticipant() {
        when(participantRepository.findById(1L)).thenReturn(Optional.of(participant));
        when(disciplineRepository.findAllById(participantRequest.getDisciplineIds())).thenReturn(Collections.singletonList(discipline));
        when(participantRepository.save(any(Participant.class))).thenReturn(participant);

        ParticipantResponseDTO response = participantService.updateParticipant(1L, participantRequest);

        assertNotNull(response);
        assertEquals(participant.getName(), response.getName());
    }

    @Test
    public void testDeleteParticipant() {
        when(participantRepository.findById(1L)).thenReturn(Optional.of(participant));
        doNothing().when(participantRepository).delete(participant);

        assertDoesNotThrow(() -> participantService.deleteParticipant(1L));
    }

    @Test
    public void testGetAllParticipants() {
        when(participantRepository.findAll()).thenReturn(Collections.singletonList(participant));

        List<ParticipantResponseDTO> response = participantService.getAllParticipants();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(participant.getName(), response.get(0).getName());
    }

    @Test
    public void testSearchParticipantsByName() {
        when(participantRepository.findByNameContainingIgnoreCase("John")).thenReturn(Collections.singletonList(participant));

        List<ParticipantResponseDTO> response = participantService.searchParticipantsByName("John");

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(participant.getName(), response.get(0).getName());
    }

}
