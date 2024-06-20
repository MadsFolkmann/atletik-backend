package Participant;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import exam.Application;
import exam.deltager.ParticipantRequestDTO;
import exam.deltager.ParticipantResponseDTO;
import exam.deltager.ParticipantService;
import exam.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ParticipantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParticipantService participantService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private ParticipantResponseDTO participantResponse;
    private ParticipantRequestDTO participantRequest;

    @BeforeEach
    void setUp() {
        participantResponse = new ParticipantResponseDTO(1L, "John Doe", Gender.MALE, 25, "Some Club", Collections.emptyList(), Collections.emptyList());
        participantRequest = new ParticipantRequestDTO("John Doe", Gender.MALE, 25, "Some Club", Collections.emptyList());
    }

    @Test
    void shouldCreateParticipant() throws Exception {
        when(participantService.createParticipant(any(ParticipantRequestDTO.class))).thenReturn(participantResponse);

        mockMvc.perform(post("/participants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(participantRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(participantResponse.getId()))
                .andExpect(jsonPath("$.name").value(participantResponse.getName()));
    }

    @Test
    void shouldGetParticipantById() throws Exception {
        when(participantService.getParticipantById(anyLong())).thenReturn(participantResponse);

        mockMvc.perform(get("/participants/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(participantResponse.getId()))
                .andExpect(jsonPath("$.name").value(participantResponse.getName()));
    }

    @Test
    void shouldUpdateParticipant() throws Exception {
        when(participantService.updateParticipant(anyLong(), any(ParticipantRequestDTO.class))).thenReturn(participantResponse);

        mockMvc.perform(put("/participants/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(participantRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(participantResponse.getId()))
                .andExpect(jsonPath("$.name").value(participantResponse.getName()));
    }

    @Test
    void shouldDeleteParticipant() throws Exception {
        mockMvc.perform(delete("/participants/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetAllParticipants() throws Exception {
        List<ParticipantResponseDTO> participants = Arrays.asList(participantResponse);
        when(participantService.getAllParticipants()).thenReturn(participants);

        mockMvc.perform(get("/participants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(participants.size()))
                .andExpect(jsonPath("$[0].id").value(participantResponse.getId()))
                .andExpect(jsonPath("$[0].name").value(participantResponse.getName()));
    }

    @Test
    void shouldSearchParticipantsByName() throws Exception {
        List<ParticipantResponseDTO> participants = Collections.singletonList(participantResponse);
        when(participantService.searchParticipantsByName(anyString())).thenReturn(participants);

        mockMvc.perform(get("/participants/search")
                        .param("name", "Hans")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(participants.size()))
                .andExpect(jsonPath("$[0].id").value(participantResponse.getId()))
                .andExpect(jsonPath("$[0].name").value(participantResponse.getName()));

        verify(participantService, times(1)).searchParticipantsByName(anyString());
    }
}