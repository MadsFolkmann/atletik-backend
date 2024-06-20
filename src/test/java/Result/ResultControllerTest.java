package Result;

import com.fasterxml.jackson.databind.ObjectMapper;
import exam.Application;
import exam.deltager.ParticipantResponseDTO;
import exam.disciplin.DisciplineResponseDTO;
import exam.enums.Gender;
import exam.enums.ResultType;
import exam.resultat.ResultRequestDTO;
import exam.resultat.ResultResponseDTO;
import exam.resultat.ResultService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.anyString;


import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResultService resultService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private ResultResponseDTO resultResponse;
    private ResultRequestDTO resultRequest;

    @BeforeEach
    void setUp() {
        DisciplineResponseDTO disciplineResponse = new DisciplineResponseDTO(1L, "100m løb", ResultType.TIME);
        ParticipantResponseDTO participantResponse = new ParticipantResponseDTO(1L, "John Doe", Gender.MALE, 25, "Some Club", Collections.emptyList(), Collections.emptyList());

        resultResponse = new ResultResponseDTO(1L, new Date(), ResultType.TIME, 10.5, disciplineResponse, participantResponse);
        resultRequest = new ResultRequestDTO(new Date(), ResultType.TIME, 10.5, 1L, 1L);
    }

    @Test
    void shouldCreateResult() throws Exception {
        when(resultService.createResult(any(ResultRequestDTO.class))).thenReturn(resultResponse);

        mockMvc.perform(post("/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resultRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(resultResponse.getId()))
                .andExpect(jsonPath("$.resultValue").value(resultResponse.getResultValue()));
    }

    @Test
    void shouldGetResultById() throws Exception {
        when(resultService.getResultById(anyLong())).thenReturn(resultResponse);

        mockMvc.perform(get("/results/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(resultResponse.getId()))
                .andExpect(jsonPath("$.resultValue").value(resultResponse.getResultValue()));
    }

    @Test
    void shouldUpdateResult() throws Exception {
        when(resultService.updateResult(anyLong(), any(ResultRequestDTO.class))).thenReturn(resultResponse);

        mockMvc.perform(put("/results/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resultRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(resultResponse.getId()))
                .andExpect(jsonPath("$.resultValue").value(resultResponse.getResultValue()));
    }

    @Test
    void shouldDeleteResult() throws Exception {
        mockMvc.perform(delete("/results/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetAllResults() throws Exception {
        List<ResultResponseDTO> results = Arrays.asList(resultResponse);
        when(resultService.getAllResults()).thenReturn(results);

        mockMvc.perform(get("/results")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(results.size()))
                .andExpect(jsonPath("$[0].id").value(resultResponse.getId()))
                .andExpect(jsonPath("$[0].resultValue").value(resultResponse.getResultValue()));
    }

    @Test
    void shouldSearchResultsByParticipantName() throws Exception {
        List<ResultResponseDTO> results = Collections.singletonList(resultResponse);
        when(resultService.searchResultsByParticipantName(anyString())).thenReturn(results);

        mockMvc.perform(get("/results/search")
                        .param("participantName", "John")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(results.size()))
                .andExpect(jsonPath("$[0].id").value(resultResponse.getId()))
                .andExpect(jsonPath("$[0].resultValue").value(resultResponse.getResultValue()));

        verify(resultService, times(1)).searchResultsByParticipantName(anyString());
    }
}
