package mykalah.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PitRepository extends JpaRepository<Pit, Long> {

    Pit save (Pit pit);
}
