package mykalah.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KalahRepository extends JpaRepository<Kalah, Long> {

    Kalah findKalahByPlayerOfKalahName(String name);
}

