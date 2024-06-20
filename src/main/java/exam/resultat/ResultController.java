package exam.resultat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping
    public ResponseEntity<ResultResponseDTO> createResult(@RequestBody ResultRequestDTO resultRequestDTO) {
        ResultResponseDTO createdResult = resultService.createResult(resultRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResult);    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponseDTO> getResultById(@PathVariable Long id) {
        ResultResponseDTO result = resultService.getResultById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultResponseDTO> updateResult(@PathVariable Long id, @RequestBody ResultRequestDTO resultRequestDTO) {
        ResultResponseDTO updatedResult = resultService.updateResult(id, resultRequestDTO);
        return ResponseEntity.ok(updatedResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ResultResponseDTO>> getAllResults() {
        List<ResultResponseDTO> results = resultService.getAllResults();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResultResponseDTO>> searchResultsByParticipantName(@RequestParam String participantName) {
        List<ResultResponseDTO> results = resultService.searchResultsByParticipantName(participantName);
        return ResponseEntity.ok(results);
    }
}
