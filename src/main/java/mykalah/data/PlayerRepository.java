package mykalah.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


//@PersistenceContext (unitName = "MailJPA")
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findPlayerByName(String username);

//    void delete (long id);
}