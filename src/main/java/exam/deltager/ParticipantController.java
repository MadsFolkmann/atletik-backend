package exam.deltager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping
    public ResponseEntity<ParticipantResponseDTO> createParticipant(@RequestBody ParticipantRequestDTO participantRequestDTO) {
        System.out.println("Received POST request: " + participantRequestDTO.toString());
        ParticipantResponseDTO createdParticipant = participantService.createParticipant(participantRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdParticipant);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantResponseDTO> getParticipantById(@PathVariable Long id) {
        ParticipantResponseDTO participant = participantService.getParticipantById(id);
        return ResponseEntity.ok(participant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipantResponseDTO> updateParticipant(@PathVariable Long id, @RequestBody ParticipantRequestDTO participantRequestDTO) {
        System.out.println("Received PUT request: " + participantRequestDTO.toString());
        ParticipantResponseDTO updatedParticipant = participantService.updateParticipant(id, participantRequestDTO);
        return ResponseEntity.ok(updatedParticipant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ParticipantResponseDTO>> getAllParticipants() {
        List<ParticipantResponseDTO> participants = participantService.getAllParticipants();
        return ResponseEntity.ok(participants);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ParticipantResponseDTO>> searchParticipants(@RequestParam String name) {
        List<ParticipantResponseDTO> participants = participantService.searchParticipantsByName(name);
        return ResponseEntity.ok(participants);
    }
}
