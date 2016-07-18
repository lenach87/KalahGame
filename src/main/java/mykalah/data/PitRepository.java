package mykalah.data;

import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by o.chubukina on 14/07/2016.
 */
@Repository
public interface PitRepository extends JpaRepository<Pit, Long> {

    Pit save (Pit pit);
}
