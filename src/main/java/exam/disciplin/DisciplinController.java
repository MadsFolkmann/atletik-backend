package exam.disciplin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discipliner")
public class DisciplinController {

    private DisciplinService disciplinService;

    public DisciplinController(DisciplinService disciplinService) {
        this.disciplinService = disciplinService;
    }

    @PostMapping
    public ResponseEntity<DisciplinResponseDTO> createDisciplin(@RequestBody DisciplinRequestDTO disciplinRequestDTO) {
        DisciplinResponseDTO createdDisciplin = disciplinService.createDisciplin(disciplinRequestDTO);
        return ResponseEntity.ok(createdDisciplin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinResponseDTO> getDisciplinById(@PathVariable Long id) {
        DisciplinResponseDTO disciplin = disciplinService.getDisciplinById(id);
        return ResponseEntity.ok(disciplin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplinResponseDTO> updateDisciplin(@PathVariable Long id, @RequestBody DisciplinRequestDTO disciplinRequestDTO) {
        DisciplinResponseDTO updatedDisciplin = disciplinService.updateDisciplin(id, disciplinRequestDTO);
        return ResponseEntity.ok(updatedDisciplin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisciplin(@PathVariable Long id) {
        disciplinService.deleteDisciplin(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DisciplinResponseDTO>> getAllDiscipliner() {
        List<DisciplinResponseDTO> discipliner = disciplinService.getAllDiscipliner();
        return ResponseEntity.ok(discipliner);
    }
}
