package exam.resultat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resultater")
public class ResultatController {

    private ResultatService resultatService;

    public ResultatController(ResultatService resultatService) {
        this.resultatService = resultatService;
    }

    @PostMapping
    public ResponseEntity<ResultatResponseDTO> createResultat(@RequestBody ResultatRequestDTO resultatRequestDTO) {
        ResultatResponseDTO createdResultat = resultatService.createResultat(resultatRequestDTO);
        return ResponseEntity.ok(createdResultat);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultatResponseDTO> getResultatById(@PathVariable Long id) {
        ResultatResponseDTO resultat = resultatService.getResultatById(id);
        return ResponseEntity.ok(resultat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultatResponseDTO> updateResultat(@PathVariable Long id, @RequestBody ResultatRequestDTO resultatRequestDTO) {
        ResultatResponseDTO updatedResultat = resultatService.updateResultat(id, resultatRequestDTO);
        return ResponseEntity.ok(updatedResultat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResultat(@PathVariable Long id) {
        resultatService.deleteResultat(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ResultatResponseDTO>> getAllResultater() {
        List<ResultatResponseDTO> resultater = resultatService.getAllResultater();
        return ResponseEntity.ok(resultater);
    }
}
