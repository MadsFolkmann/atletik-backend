package exam;
import exam.deltager.Participant;
import exam.deltager.ParticipantRepository;
import exam.disciplin.Discipline;
import exam.disciplin.DisciplineRepository;
import exam.enums.Gender;
import exam.enums.ResultType;
import exam.resultat.Result;
import exam.resultat.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    private final ParticipantRepository participantRepository;
    private final DisciplineRepository disciplineRepository;
    private final ResultRepository resultRepository;

    public InitData(ParticipantRepository participantRepository, DisciplineRepository disciplineRepository, ResultRepository resultRepository) {
        this.participantRepository = participantRepository;
        this.disciplineRepository = disciplineRepository;
        this.resultRepository = resultRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initDisciplines();
        initParticipants();
        initResults();
    }

    private void initDisciplines() {
        List<Discipline> disciplines = Arrays.asList(
                new Discipline(null, "100m run", ResultType.TIME, null, null),
                new Discipline(null, "Discus throw", ResultType.DISTANCE, null, null),
                new Discipline(null, "Triple jump", ResultType.DISTANCE, null, null),
                new Discipline(null, "Marathon", ResultType.TIME, null, null),
                new Discipline(null, "High jump", ResultType.DISTANCE, null, null)
        );
        disciplineRepository.saveAll(disciplines);
    }

    private void initParticipants() {
        Discipline run100m = disciplineRepository.findByName("100m run").get(0);
        Discipline discusThrow = disciplineRepository.findByName("Discus throw").get(0);
        Discipline tripleJump = disciplineRepository.findByName("Triple jump").get(0);

        List<Participant> participants = Arrays.asList(
                new Participant(null, "Hans Hansen", Gender.MALE, 25, "Athletics Club", Arrays.asList(run100m, discusThrow), null),
                new Participant(null, "Peter Petersen", Gender.MALE, 30, "Athletics Club", Arrays.asList(run100m, tripleJump), null),
                new Participant(null, "Mette Madsen", Gender.FEMALE, 22, "Athletics Club", Arrays.asList(run100m), null),
                new Participant(null, "Janne Jensen", Gender.FEMALE, 27, "Athletics Club", Arrays.asList(discusThrow, tripleJump), null)
        );
        participantRepository.saveAll(participants);
    }

    private void initResults() {
        Participant hans = participantRepository.findByName("Hans Hansen").get(0);
        Participant peter = participantRepository.findByName("Peter Petersen").get(0);
        Participant mette = participantRepository.findByName("Mette Madsen").get(0);
        Participant janne = participantRepository.findByName("Janne Jensen").get(0);

        Discipline run100m = disciplineRepository.findByName("100m run").get(0);
        Discipline discusThrow = disciplineRepository.findByName("Discus throw").get(0);
        Discipline tripleJump = disciplineRepository.findByName("Triple jump").get(0);

        List<Result> results = Arrays.asList(
                new Result(null, new Date(), ResultType.TIME, 12.34, run100m, hans),
                new Result(null, new Date(), ResultType.TIME, 11.56, run100m, peter),
                new Result(null, new Date(), ResultType.TIME, 13.78, run100m, mette),
                new Result(null, new Date(), ResultType.DISTANCE, 40.5, discusThrow, hans),
                new Result(null, new Date(), ResultType.DISTANCE, 38.7, discusThrow, janne),
                new Result(null, new Date(), ResultType.DISTANCE, 13.4, tripleJump, peter),
                new Result(null, new Date(), ResultType.DISTANCE, 14.2, tripleJump, hans)
        );
        resultRepository.saveAll(results);
    }
}