package exam.disciplin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplines")
public class DisciplineController {

    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @PostMapping
    public ResponseEntity<DisciplineResponseDTO> createDiscipline(@RequestBody DisciplineRequestDTO disciplineRequestDTO) {
        DisciplineResponseDTO createdDiscipline = disciplineService.createDiscipline(disciplineRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscipline);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineResponseDTO> getDisciplineById(@PathVariable Long id) {
        DisciplineResponseDTO discipline = disciplineService.getDisciplineById(id);
        return ResponseEntity.ok(discipline);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplineResponseDTO> updateDiscipline(@PathVariable Long id, @RequestBody DisciplineRequestDTO disciplineRequestDTO) {
        DisciplineResponseDTO updatedDiscipline = disciplineService.updateDiscipline(id, disciplineRequestDTO);
        return ResponseEntity.ok(updatedDiscipline);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscipline(@PathVariable Long id) {
        disciplineService.deleteDiscipline(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DisciplineResponseDTO>> getAllDisciplines() {
        List<DisciplineResponseDTO> disciplines = disciplineService.getAllDisciplines();
        return ResponseEntity.ok(disciplines);
    }
}