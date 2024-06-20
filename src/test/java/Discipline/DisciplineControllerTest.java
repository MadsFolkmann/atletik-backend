package Discipline;
import com.fasterxml.jackson.databind.ObjectMapper;
import exam.Application;
import exam.disciplin.DisciplineRequestDTO;
import exam.disciplin.DisciplineResponseDTO;
import exam.disciplin.DisciplineService;
import exam.enums.ResultType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;


@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class DisciplineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DisciplineService disciplineService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private DisciplineResponseDTO disciplineResponse;
    private DisciplineRequestDTO disciplineRequest;

    @BeforeEach
    void setUp() {
        disciplineResponse = new DisciplineResponseDTO(1L, "100m Sprint", ResultType.TIME);
        disciplineRequest = new DisciplineRequestDTO("100m Sprint", ResultType.TIME);
    }

    @Test
    void shouldCreateDiscipline() throws Exception {
        when(disciplineService.createDiscipline(any(DisciplineRequestDTO.class))).thenReturn(disciplineResponse);

        mockMvc.perform(post("/disciplines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(disciplineRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(disciplineResponse.getId()))
                .andExpect(jsonPath("$.name").value(disciplineResponse.getName()))
                .andExpect(jsonPath("$.resultType").value(disciplineResponse.getResultType().toString()));
    }

    @Test
    void shouldGetDisciplineById() throws Exception {
        when(disciplineService.getDisciplineById(anyLong())).thenReturn(disciplineResponse);

        mockMvc.perform(get("/disciplines/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(disciplineResponse.getId()))
                .andExpect(jsonPath("$.name").value(disciplineResponse.getName()))
                .andExpect(jsonPath("$.resultType").value(disciplineResponse.getResultType().toString()));
    }

    @Test
    void shouldUpdateDiscipline() throws Exception {
        when(disciplineService.updateDiscipline(anyLong(), any(DisciplineRequestDTO.class))).thenReturn(disciplineResponse);

        mockMvc.perform(put("/disciplines/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(disciplineRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(disciplineResponse.getId()))
                .andExpect(jsonPath("$.name").value(disciplineResponse.getName()))
                .andExpect(jsonPath("$.resultType").value(disciplineResponse.getResultType().toString()));
    }

    @Test
    void shouldDeleteDiscipline() throws Exception {
        mockMvc.perform(delete("/disciplines/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetAllDisciplines() throws Exception {
        List<DisciplineResponseDTO> disciplines = Arrays.asList(disciplineResponse);
        when(disciplineService.getAllDisciplines()).thenReturn(disciplines);

        mockMvc.perform(get("/disciplines")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(disciplines.size()))
                .andExpect(jsonPath("$[0].id").value(disciplineResponse.getId()))
                .andExpect(jsonPath("$[0].name").value(disciplineResponse.getName()))
                .andExpect(jsonPath("$[0].resultType").value(disciplineResponse.getResultType().toString()));
    }
}
