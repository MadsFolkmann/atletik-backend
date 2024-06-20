package exam.resultat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByParticipantNameContainingIgnoreCase(String participantName);

}