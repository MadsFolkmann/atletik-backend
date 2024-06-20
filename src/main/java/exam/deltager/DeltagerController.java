package exam.deltager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deltagere")
public class DeltagerController {

    private DeltagerService deltagerService;

    public DeltagerController(DeltagerService deltagerService) {
        this.deltagerService = deltagerService;
    }

    @PostMapping
    public ResponseEntity<DeltagerResponseDTO> createDeltager(@RequestBody DeltagerRequestDTO deltagerRequestDTO) {
        DeltagerResponseDTO createdDeltager = deltagerService.createDeltager(deltagerRequestDTO);
        return ResponseEntity.ok(createdDeltager);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeltagerResponseDTO> getDeltagerById(@PathVariable Long id) {
        DeltagerResponseDTO deltager = deltagerService.getDeltagerById(id);
        return ResponseEntity.ok(deltager);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeltagerResponseDTO> updateDeltager(@PathVariable Long id, @RequestBody DeltagerRequestDTO deltagerRequestDTO) {
        DeltagerResponseDTO updatedDeltager = deltagerService.updateDeltager(id, deltagerRequestDTO);
        return ResponseEntity.ok(updatedDeltager);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeltager(@PathVariable Long id) {
        deltagerService.deleteDeltager(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DeltagerResponseDTO>> getAllDeltagere() {
        List<DeltagerResponseDTO> deltagere = deltagerService.getAllDeltagere();
        return ResponseEntity.ok(deltagere);
    }
}
