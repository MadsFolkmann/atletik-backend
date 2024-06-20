package exam.deltager;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

 public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByName(String name);

     List<Participant> findByNameContainingIgnoreCase(String name);

 }